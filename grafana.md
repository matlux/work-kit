## Overview

A small Docker Compose stack in `/home/ad/compose` runs:

- **Loki** (`grafana/loki:latest`) exposed on **port 3100** — stores logs locally with **7‑day retention**.
- **Promtail** (`grafana/promtail:latest`) — tails the **systemd journal** and forwards **only `veranad.service`** logs to Loki.
- **Prometheus** (`prom/prometheus:latest`) exposed on **port 9091** (host) → 9090 (container) — scrapes **CometBFT** metrics from `node1.devnet.verana.network:26660`.

Grafana (running elsewhere) adds a **Loki** data source pointing at:

```
http://node1.devnet.verana.network:3100
```

In **Explore → Logs**, you can query:

```logql
{job="systemd-journal", unit="veranad.service"}
```

---

## Directory layout (host)

```
/home/ad/compose/
  docker-compose.yml
  loki-config.yml
  promtail-config.yml
  loki-data/            # Loki chunks/index/cache on host
  wal/                  # Loki WAL on host
  prometheus/
    prometheus.yml
```

---

## Configuration (as-deployed)

### `docker-compose.yml`

- Brings up three services with `restart: unless-stopped`.
- Publishes: **`3100:3100`** (Loki), **`9091:9090`** (Prometheus).
- Mounts:
  - `./loki-config.yml` → `/etc/loki/loki-config.yml`
  - `./loki-data` → `/loki` and `./wal` → `/wal` (Loki storage)
  - `./promtail-config.yml` → `/etc/promtail/promtail-config.yml`
  - `/var/log/journal`, `/run/log/journal`, `/etc/machine-id` (read‑only) and `/tmp/promtail` (positions) into Promtail.

```yaml
version: '3.8'

services:
  prometheus:
    image: prom/prometheus:latest
    container_name: prometheus
    ports:
      - "9091:9090"
    volumes:
      - ./prometheus/prometheus.yml:/etc/prometheus/prometheus.yml
    restart: unless-stopped

  loki:
    image: grafana/loki:latest
    container_name: loki
    ports:
      - "3100:3100"
    command: -config.file=/etc/loki/loki-config.yml
    volumes:
      - ./loki-config.yml:/etc/loki/loki-config.yml
      - ./loki-data:/loki
      - ./wal:/wal
    restart: unless-stopped

  promtail:
    image: grafana/promtail:latest
    container_name: promtail
    command: -config.file=/etc/promtail/promtail-config.yml
    volumes:
      - ./promtail-config.yml:/etc/promtail/promtail-config.yml
      - /var/log/journal:/var/log/journal:ro
      - /run/log/journal:/run/log/journal:ro
      - /etc/machine-id:/etc/machine-id:ro
      - /tmp/promtail:/tmp  # for positions
    restart: unless-stopped
```

---

### `promtail-config.yml`

- Sends to: `http://loki:3100/loki/api/v1/push`.
- Positions file: `/tmp/promtail/positions.yaml` (mapped to host `/tmp/promtail` so it **persists** across container restarts).
- **Only** scrapes journald entries for `veranad.service`.

```yaml
server:
  http_listen_port: 9080
  grpc_listen_port: 0

positions:
  filename: /tmp/promtail/positions.yaml

clients:
  - url: http://loki:3100/loki/api/v1/push

scrape_configs:
  - job_name: journal_veranad
    journal:
      path: /var/log/journal
      labels:
        job: systemd-journal
        unit: veranad.service
    relabel_configs:
      - source_labels: ['__journal__systemd_unit']
        regex: 'veranad\.service'
        action: keep
```

---

### `loki-config.yml`

- `auth_enabled: false` — port **3100** is openly readable if reachable.
- Storage: **boltdb-shipper** + **filesystem** under `/loki` (host path `./loki-data`).
- Retention: **`168h` (7 days)** via `table_manager.retention_period`.

```yaml
auth_enabled: false

server:
  http_listen_port: 3100
  grpc_listen_port: 9095

ingester:
  lifecycler:
    ring:
      kvstore:
        store: inmemory
      replication_factor: 1
  chunk_idle_period: 5m
  max_chunk_age: 1h

schema_config:
  configs:
    - from: 2024-01-01
      store: boltdb-shipper
      object_store: filesystem
      schema: v11
      index:
        prefix: index_
        period: 24h

storage_config:
  boltdb_shipper:
    active_index_directory: /loki/index
    cache_location: /loki/boltdb-cache
  filesystem:
    directory: /loki/chunks

limits_config:
  allow_structured_metadata: false

compactor:
  working_directory: /loki/compactor

table_manager:
  retention_deletes_enabled: true
  retention_period: 168h
```

---

### `prometheus/prometheus.yml`

- Scrapes CometBFT metrics every **15s** from `node1.devnet.verana.network:26660` under job `cometbft-node`.
- Grafana can add a Prometheus data source pointing at `http://node1.devnet.verana.network:9091`.

```yaml
global:
  scrape_interval: 15s

scrape_configs:
  - job_name: 'cometbft-node'
    static_configs:
      - targets: ['node1.devnet.verana.network:26660']
```

---

### Journald

- `/etc/systemd/journald.conf` is at defaults (`Storage=auto` commented).
- Promtail mounts **both** `/var/log/journal` and `/run/log/journal` (read‑only), so it works with persistent or runtime journals.

To enable **persistent** journald logs across reboots:

```bash
sudo mkdir -p /var/log/journal
sudo systemctl restart systemd-journald
```

---

## Reproduce / Reinstall

```bash
# 1) Install Docker & docker-compose (already on host)
#    (Using Docker apt repo + docker-compose package or plugin)

# 2) Create the stack directory and files
sudo mkdir -p /home/ad/compose/prometheus /home/ad/compose/loki-data /home/ad/compose/wal
cd /home/ad/compose

# 3) Put these files in place (use the contents above)
#    docker-compose.yml
#    loki-config.yml
#    promtail-config.yml
#    prometheus/prometheus.yml

# 4) (Optional) Make journald persistent
sudo mkdir -p /var/log/journal && sudo systemctl restart systemd-journald

# 5) Launch the stack
docker-compose up -d

# 6) Verify
docker ps
curl -s http://localhost:3100/ready     # should return "ready"
docker logs -f promtail                 # see shipping activity
```

---

## Using it in Grafana

### Loki data source

- **URL:** `http://node1.devnet.verana.network:3100`
- **Access:** Server (default)
- **Auth:** none (currently open)

**Quick queries**

All veranad logs:

```logql
{job="systemd-journal", unit="veranad.service"}
```

Filter for errors:

```logql
{job="systemd-journal", unit="veranad.service"} |= "ERR"
```

### Prometheus data source (optional)

- **URL:** `http://node1.devnet.verana.network:9091`
- Query CometBFT metrics (e.g. `consensus_height`, etc.) if exported.

---

## Operations

```bash
# Check status
cd /home/ad/compose
docker-compose ps
docker ps

# Tail logs
docker logs -f loki
docker logs -f promtail
docker logs -f prometheus

# Update images and restart
docker-compose pull
docker-compose up -d

# Stop / Start
docker-compose down
docker-compose up -d
```

---

## Security notes (important)

- Loki auth is **disabled** and port **3100** is **exposed**. Anyone who can reach the host can query logs.

Quick hardening options:

- Bind Loki to localhost only and reverse‑proxy via Nginx with auth:
  ```yaml
  # in docker-compose.yml
  # remove "3100:3100" and add:
  ports:
    - "127.0.0.1:3100:3100"
  ```
- Firewall **3100** to trusted CIDRs.
- Enable auth in Loki and restrict clients.

Promtail reads the system journal read‑only; keep `/etc/machine-id` mounted RO as done.

---

## Retention & storage

- Current retention: **7 days**. To change, edit `table_manager.retention_period` in `loki-config.yml` (e.g. `720h` for 30 days), then:

```bash
docker-compose up -d
```

- Data on host: `/home/ad/compose/loki-data` (safe to back up/restore with Loki stopped).

---

## Uninstall / remove

```bash
cd /home/ad/compose
docker-compose down
sudo rm -rf /home/ad/compose/loki-data /home/ad/compose/wal /home/ad/compose/prometheus
# Remove the directory if desired:
# sudo rm -rf /home/ad/compose
```

---

## Why Grafana shows a `loki-node1-…` source name

Grafana has a Loki data source configured (named to indicate node1), pointing to this host’s Loki at **3100**. Promtail’s config ships only `veranad.service` logs, so that’s what you see in **Explore**.