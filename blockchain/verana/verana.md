


# how to transfer balance from account (often faucet)

```
veranad tx bank send cooluser <faucet_address> 1000000000uvna --from cooluser --chain-id $CHAIN_ID --keyring-backend test -y --fees 600000uvna
```


```
SENDER=faucet
SENDER_LIT=verana167vrykn5vhp8v9rng69xf0jzvqa3v79etmr0t2
RECEIVER=cooluser
RECEIVER_LIT=verana16mzeyu9l6kua2cdg9x0jk5g6e7h0kk8q6uadu4
veranad tx bank send $SENDER $RECEIVER_LIT 100000000uvna --from $SENDER --chain-id $CHAIN_ID --keyring-backend test --fees 600000uvna --node $NODE_RPC
```

# how to check balance

```
USER_ACC_LIT=cosmos1xr7fuv6kcgfzw9mct8lj3v2y2fkehjheqqpza6
veranad q bank balances $USER_ACC_LIT --chain-id $CHAIN_ID --node $NODE_RPC
```

or for a specific token like uvna
```
veranad q bank balance $USER_ACC uvna
```

# How to submit proposal

```
veranad tx gov submit-proposal draft_proposal.json --from cooluser --chain-id vna-testnet-1 --keyring-backend test --fees 60000uvna --node $NODE_RPC
```

# List all module accounts

```
veranad q auth module-accounts
```


# How many tokens are delegated?
```bash
veranad q staking delegations $USER_ACC
```

# List all local account on the client side's keyring
```bash
veranad keys list --keyring-backend test
```

# Give me the tally for a proposal
```bash
veranad q gov tally 1 --output json --node $NODE_RPC
```

# How to check a transaction?

```bash
TX_HASH=9DF2F9D35F6702A875E015825D80A26DEFADE5C47E025D612327216D2521098A
veranad q tx $TX_HASH  --node $NODE_RPC
```

# How to query a transaction by height?

```bash
veranad q block --type=height 990976 --node $NODE_RPC
```


# How to query a transaction time?

```bash
veranad q block --type=height 990976 --node $NODE_RPC -o json | jq -r '.header.time' 
```

# Validator troubleshooting quick kit

## The 3 validator addresses (and which keys they come from)

Here are the three addresses derived from the validator key in this test
keyring and confirmed via validator1 RPC:

- Account: verana1z2epxhjn6qrg0uca6j0rq7llupe3n0nl0gjt7d
- Valoper: veranavaloper1z2epxhjn6qrg0uca6j0rq7llupe3n0nlw8e6zd
- Valcons: veranavalcons18z8zxdnj8mv25pn2hrkj5268f8hurj08mlggfs

Key relationship (short answer):
- Account (verana1...) and valoper (veranavaloper1...) are the same keypair, just different bech32 prefixes of the same
  public key. They share the same private key.
- Valcons (veranavalcons1...) is derived from the consensus keypair (ed25519), which is separate. It does not share the same
  private key as the account/valoper.

## Derive the three addresses from your local keyring / node

```bash
VAL_KEY_NAME=validator
veranad keys show "$VAL_KEY_NAME" -a --keyring-backend test
veranad keys show "$VAL_KEY_NAME" --bech val -a --keyring-backend test

# If you are on the validator node:
veranad tendermint show-address
```

If you are not on the validator node, you can derive the valcons from the consensus pubkey published by the chain:

```bash
API_ENDPOINT="https://api.testnet.verana.network"
VALOPER=$(veranad keys show "$VAL_KEY_NAME" --bech val -a --keyring-backend test)
CONS_PUBKEY_JSON=$(curl -s "$API_ENDPOINT/cosmos/staking/v1beta1/validators/$VALOPER" \
  | jq -c '.validator.consensus_pubkey')
HEX=$(veranad debug pubkey "$CONS_PUBKEY_JSON" | awk -F': ' '/Address:/{print $2}')
veranad debug addr "$HEX"
```

## Understand what `veranad debug addr` does (hex -> bech32)

```bash
veranad debug addr 388E2336723ED8AA066AB8ED2A2B4749EFC1C9E7
```

This takes a hex address and prints the corresponding bech32 variants (account, valoper, valcons). Use it when a log or
JSON object gives you a hex address and you need the human-friendly prefixes.

## Validate a raw consensus pubkey (base64 + ed25519)

```bash
veranad debug pubkey-raw mUNO2bzyVU1hezYD7io7KoOayyakW4lt3RXF4S45U8c= -t ed25519
```

This converts a base64-encoded ed25519 pubkey to its address form(s) so you can verify the consensus identity used for
signing. It is commonly used to sanity-check the consensus pubkey embedded in a gentx or returned by the staking API.

## Get validator info quickly

```bash
CHAIN_ID="vna-testnet-1"
NODE_RPC="http://validator1.testnet.verana.network:26657"
VALOPER="veranavaloper1z2epxhjn6qrg0uca6j0rq7llupe3n0nlw8e6zd"

veranad query staking validator "$VALOPER" \
  --node "$NODE_RPC" \
  --chain-id "$CHAIN_ID" -o json | jq
```

Gotcha: the response is nested under `.validator`, so jq paths must include that key:

```bash
veranad query staking validator "$VALOPER" \
  --node "$NODE_RPC" \
  --chain-id "$CHAIN_ID" -o json \
| jq -r '.validator.description.moniker, .validator.status, .validator.jailed, .validator.tokens'
```

Also useful: get the valoper from a local key name:

```bash
veranad keys show validator --bech val -a --keyring-backend test
```

## Check if a validator is jailed + signing info (valcons)

```bash
CHAIN_ID="vna-testnet-1"
NODE_RPC="http://validator1.testnet.verana.network:26657"
VALCONS="veranavalcons18z8zxdnj8mv25pn2hrkj5268f8hurj08mlggfs"

veranad query slashing signing-info "$VALCONS" \
  --node "$NODE_RPC" \
  --chain-id "$CHAIN_ID" -o json | jq -r '.val_signing_info'
```

## Validator status checklist (fast triage)

```bash
CHAIN_ID="vna-testnet-1"
NODE_RPC="http://validator1.testnet.verana.network:26657"
VALOPER="veranavaloper1z2epxhjn6qrg0uca6j0rq7llupe3n0nlw8e6zd"

# 1) Is the validator bonded / jailed / active?
veranad query staking validator "$VALOPER" \
  --node "$NODE_RPC" \
  --chain-id "$CHAIN_ID" -o json \
| jq -r '.validator.status, .validator.jailed, .validator.tokens'

# 2) Is the node synced and making progress?
curl -s "$NODE_RPC/status" | jq -r '.result.sync_info.catching_up, .result.sync_info.latest_block_height'

# 3) Is the node connected to peers?
curl -s "$NODE_RPC/net_info" | jq -r '.result.n_peers'
```

If any of these look wrong, check logs:

```bash
journalctl -u veranad -f
```

## Unjail a validator (copy/paste ready)

```bash
CHAIN_ID="vna-testnet-1"
NODE_RPC="http://validator1.testnet.verana.network:26657"
VAL_KEY_NAME="validator"

veranad tx slashing unjail \
  --from "$VAL_KEY_NAME" \
  --chain-id "$CHAIN_ID" \
  --node "$NODE_RPC" \
  --keyring-backend test \
  --fees 600000uvna \
  --yes
```

## Unjail prerequisites + gotchas

- Unjail must be signed by the operator account key (same keypair as `verana1...` and `veranavaloper1...`).
- You can only unjail after the downtime jail window has elapsed; check signing info first.
- If you use `--dry-run`, `--from` must be a bech32 account address (key names are not accepted).
- If the node is still catching up, unjail may succeed but you will keep missing blocks; ensure sync is complete first.
