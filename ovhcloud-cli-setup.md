# OVHcloud CLI Setup (Local)

This doc captures the working setup used on macOS with OVHcloud CA.

## Install

Homebrew install:

```bash
brew install --cask ovh/tap/ovhcloud-cli
```

If macOS blocks the binary, remove quarantine:

```bash
xattr -d com.apple.quarantine /opt/homebrew/bin/ovhcloud
```

## Config

The CLI loads config from the following locations (in this order):

1) `./ovh.conf`
2) `~/.ovh.conf`
3) `/etc/ovh.conf`

Example `~/.ovh.conf` for OVHcloud CA:

```ini
[default]
endpoint=ovh-ca

[ovh-ca]
application_key=YOUR_APP_KEY
application_secret=YOUR_APP_SECRET
consumer_key=YOUR_CONSUMER_KEY
```

Note: credentials must be under the section that matches the `endpoint`.

## Create API Keys (OVHcloud CA UI)

Broad steps:

1) Log into `manager.ca.ovhcloud.com`.
2) Go to `Identity, Security & Operations` -> `API keys`.
3) Create a new API key with read-only GET rights:
   - `/cloud/project`
   - `/cloud/project/*`
   - `/cloud/project/*/instance/*`
   - `/cloud/project/*/volume/*`
   - `/cloud/project/*/network/*`
   - `/cloud/project/*/region/*`
4) Save the `application_key`, `application_secret`, and `consumer_key` values.

Note: include the `.../region/*` right; the first token failed without it.
When adding rights in the UI, each line must be confirmed by clicking the `+`
button, otherwise it is not included.

Those three values go into `~/.ovh.conf` (or `./ovh.conf`) under the `[ovh-ca]` section.

## Verify

```bash
ovhcloud cloud project list
```
