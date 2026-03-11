# OpenClaw on Hetzner VM

Notes for provisioning a reusable Hetzner VM for OpenClaw, a Linux desktop, and private remote access.

## Goal

- Run OpenClaw on an always-on VPS rather than a metered desktop provider.
- Add a lightweight Linux desktop for browser-based work and remote administration.
- Keep the machine private by default and avoid exposing OpenClaw or RDP directly to the public internet.

## Chosen Setup

- Provider: Hetzner Cloud
- Server name: `openclaw-1`
- Plan: `CX33`
- Image: `Ubuntu 24.04`
- Location used in this run: `Helsinki`
- Current server IP in this run: `204.168.136.254`
- Desktop: `XFCE`
- Remote desktop: `xrdp`
- Private connectivity: `Tailscale`
- Browser for OpenClaw automation: `Brave`
- OpenClaw install mode: native install on the host

## Why This Setup

- Cheaper and more persistent than Orgo for a 24/7 OpenClaw host.
- Simpler than building a multi-node private network up front.
- Keeps OpenClaw state, credentials, and browser profiles on one persistent machine.
- `CX33` gives headroom for desktop + browser + OpenClaw without starting too tight.

## Hetzner Console Choices

### Server creation

- Type: `CX33`
- Location: any nearby EU region is fine
- Image: `Ubuntu 24.04`
- Public IPv4: enabled
- Public IPv6: enabled
- Private network: disabled for a single-server setup
- SSH key: add the local machine SSH public key before creating the server
- Volumes: none
- Backups: disabled initially
- Placement group: disabled
- Labels: empty
- Cloud config: empty
- Name: `openclaw-1`

### Firewall

Create a firewall named `openclaw-base` with:

- Inbound `TCP 22` from the current public IP only
- Optional inbound `UDP 41641` from anywhere for easier Tailscale connectivity
- No public `3389`
- No public `18789`
- No outbound rules

Attach the firewall during server creation.

## Initial Login

From the local machine:

```bash
ssh root@204.168.136.254
```

## Create the Admin User

Create a normal user and copy the root SSH authorized keys to it.

```bash
adduser mathieu
usermod -aG sudo mathieu
install -d -m 700 -o mathieu -g mathieu /home/mathieu/.ssh
cp /root/.ssh/authorized_keys /home/mathieu/.ssh/authorized_keys
chown mathieu:mathieu /home/mathieu/.ssh/authorized_keys
chmod 600 /home/mathieu/.ssh/authorized_keys
```

Notes:

- `adduser` asks for a Linux password.
- That password is needed later for `sudo` and `xrdp` desktop login.
- SSH should still use the SSH key by default.

Test the user from a second local terminal before doing anything else:

```bash
ssh mathieu@204.168.136.254
```

## Install the Desktop

Run as the normal user:

```bash
sudo apt update && sudo apt upgrade -y
sudo apt install -y xfce4 xfce4-goodies xrdp xorgxrdp dbus-x11 curl ca-certificates git
echo startxfce4 > ~/.xsession
sudo systemctl enable --now xrdp
sudo adduser xrdp ssl-cert
```

Notes:

- `xrdp` provides remote desktop access.
- RDP stays private until Tailscale is configured and the firewall remains closed to public `3389`.

## Install Tailscale

```bash
curl -fsSL https://tailscale.com/install.sh | sh
sudo tailscale up
```

Notes:

- Open the login URL that `tailscale up` prints.
- Install Tailscale on the local Mac as well and join the same tailnet.
- Once connected, use the Tailscale IP or MagicDNS hostname instead of the public IP whenever possible.

## Install Brave

```bash
sudo curl -fsSLo /usr/share/keyrings/brave-browser-archive-keyring.gpg \
  https://brave-browser-apt-release.s3.brave.com/brave-browser-archive-keyring.gpg

sudo curl -fsSLo /etc/apt/sources.list.d/brave-browser-release.sources \
  https://brave-browser-apt-release.s3.brave.com/brave-browser.sources

sudo apt update
sudo apt install -y brave-browser
```

## Install OpenClaw

```bash
curl -fsSL https://openclaw.ai/install.sh | bash
command -v openclaw
```

If `openclaw` lands somewhere unusual, note the path before creating a service.

## OpenClaw Configuration Plan

The intended configuration for this VM:

- Run OpenClaw natively on the host
- Keep the Gateway bound to loopback
- Reach it from the Mac using OpenClaw remote-over-SSH mode
- Use the Linux desktop for browser/OAuth flows when needed

Planned onboarding command:

```bash
openclaw onboard
```

Expected choices:

- Mode: `Advanced`
- Gateway bind: `loopback`
- Gateway port: `18789`
- Gateway auth: `token`
- Workspace: default unless there is a reason to change it

## Planned Service

After onboarding, install OpenClaw as a persistent systemd service.

Example unit:

```ini
[Unit]
Description=OpenClaw Gateway
After=network-online.target
Wants=network-online.target

[Service]
Type=simple
User=mathieu
WorkingDirectory=/home/mathieu
Environment=HOME=/home/mathieu
Environment=PATH=/usr/local/bin:/usr/bin:/bin:/home/mathieu/.local/bin
ExecStart=/usr/local/bin/openclaw gateway --port 18789
Restart=always
RestartSec=5

[Install]
WantedBy=multi-user.target
```

## Remote Access Model

### SSH

- Primary admin path
- Use SSH key auth
- Restrict public `22` in the firewall to the current public IP

### Desktop

- Use `xrdp`
- Connect over Tailscale, not over the public internet
- Login with the Linux username and password created by `adduser`

### OpenClaw

- Keep the Gateway private
- Access it from the Mac using OpenClaw remote-over-SSH mode

## Security Notes

- Do not expose `3389` publicly.
- Do not expose `18789` publicly.
- Prefer Tailscale for private connectivity.
- Once the normal user is confirmed working, disable SSH root login.
- Keep SSH key-based access enabled.

Suggested hardening after setup is stable:

```bash
sudo sed -i 's/^#\\?PermitRootLogin.*/PermitRootLogin no/' /etc/ssh/sshd_config
sudo systemctl reload ssh
```

## Current Status In This Run

Completed:

- Hetzner account created
- Project created
- SSH key added
- Firewall created
- Server `openclaw-1` created
- Public IP assigned: `204.168.136.254`

In progress:

- Creating normal admin user
- Installing desktop, Tailscale, Brave, and OpenClaw

## Repeat Checklist

1. Create server in Hetzner with `Ubuntu 24.04` and `CX33`.
2. Add firewall with only SSH open from the current IP.
3. SSH in as `root`.
4. Create normal sudo user and verify SSH access for that user.
5. Install `XFCE`, `xrdp`, and base packages.
6. Install and join `Tailscale`.
7. Install `Brave`.
8. Install `OpenClaw`.
9. Run `openclaw onboard`.
10. Install systemd service for the OpenClaw Gateway.
11. Disable root SSH login after verifying normal-user access.

