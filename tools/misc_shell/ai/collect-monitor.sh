#!/usr/bin/env bash
set -euo pipefail

sep(){ printf '\n===== BEGIN %s =====\n' "$1"; }
end(){ printf '===== END %s =====\n' "$1"; }
dump(){ local f="$1"; sep "$f"; (cat "$f" 2>/dev/null || sudo cat "$f" 2>/dev/null) || echo "[MISSING]"; end "$f"; }

echo "# Host: $(hostname) | Date: $(date -Is)"

# --- Common stack paths (adjusts per host; missing files will show [MISSING]) ---
dump "/home/ad/compose/docker-compose.yml"
dump "/home/ad/compose/loki-config.yml"
dump "/home/ad/compose/promtail-config.yml"
dump "/home/ad/compose/prometheus/prometheus.yml"

# --- Grafana provisioning (if this is the Grafana server) ---
dump "/etc/grafana/grafana.ini"

GDS="/etc/grafana/provisioning/datasources"
if [ -d "$GDS" ]; then
  for f in "$GDS"/*.yml "$GDS"/*.yaml; do
    [ -e "$f" ] || continue
    dump "$f"
  done
else
  sep "$GDS"; echo "[Directory not present]"; end "$GDS"
fi

# If Grafana runs in Docker, these are common:
dump "/opt/grafana/conf/provisioning/datasources/datasources.yml"
dump "/etc/grafana/provisioning/dashboards/dashboards.yml"