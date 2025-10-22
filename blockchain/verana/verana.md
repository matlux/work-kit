


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
```
veranad q staking delegations $USER_ACC
```

# List all local account on the client side's keyring
```
veranad keys list --keyring-backend test
```

# Give me the tally for a proposal
```
veranad q gov tally 1 --output json
```

# How to check a transaction?

```
TX_HASH=9DF2F9D35F6702A875E015825D80A26DEFADE5C47E025D612327216D2521098A
veranad q tx $TX_HASH  --node $NODE_RPC
```