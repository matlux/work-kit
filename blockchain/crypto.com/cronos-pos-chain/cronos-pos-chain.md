
# chain-maind

https://help.crypto.com/en/articles/5015391-all-about-staking-cro-on-defi-earn


`chain-maind` is the all-in-one command-line interface. It supports wallet management, funds transfer, and staking operations.

## Build and Configurations

### Build Prerequisites

- Get the latest `chain-maind` binary from the [release page](https://github.com).
- Install via [homebrew](https://brew.sh).

```sh
brew tap crypto-org-chain/chain-maind
brew install chain-maind
chain-maind
```

### Using `chain-maind`

Run:

```sh
chain-maind [command]
chain-maind -h
```

### Config and Data Directory

Default location: `~/.chain-maind`. Back up your wallet storage after creating the wallet.

#### Specify Config and Data Directory

Add global flag `--home <directory>`.

### Configure Chain ID

Set up your chain-maind and use the correct configuration:

```sh
alias chain-maind="chain-maind --chain-id crypto-org-chain-mainnet-1"
```

### Options

| Option               | Description           | Type   | Default Value       |
|----------------------|-----------------------|--------|---------------------|
| `--home`             | Directory for config  | string | `~/.chain-maind`    |
| `--chain-id`         | Full Chain ID         | string | ---                 |
| `--output`           | Output format         | string | "text"              |
| `--keyring-backend`  | Select keyring's backend | os/file/test | os |

## Command List

Commonly used `chain-maind` commands.

### Keys Management - `chain-maind keys`

Create, recover, list, show, delete, and export keys.

#### Create a New Key

```sh
chain-maind keys add Default
```

#### Restore Existing Key

```sh
chain-maind keys add Default_restore --recover
```

#### List Your Keys

```sh
chain-maind keys list
```

#### Retrieve Key Information

```sh
chain-maind keys show Default --bech acc
chain-maind keys show Default --bech val
chain-maind keys show Default --bech cons
```

#### Delete a Key

```sh
chain-maind keys delete Default_restore1
```

#### Export Private Keys

```sh
chain-maind keys export Default
```

### Keyring Backend Option

```sh
chain-maind keys [subcommands] --keyring-backend [backend type]
```

### Transactions Subcommands - `chain-maind tx`

#### Transfer Operation

Send funds:

```sh
chain-maind tx bank send Default cro17waz6n5a4c4z388rvc40n4c402njfjgqmv0qcp 10cro --chain-id crypto-org-chain-mainnet-1
```

#### Staking Operations

Delegate funds:

```sh
chain-maind tx staking delegate crocncl1zdlttjrqh9jsgk2l8tgn6f0kxlfy98s3prz35z 100cro --from Default --chain-id crypto-org-chain-mainnet-1
```

Unbond funds:

```sh
chain-maind tx staking unbond crocncl1zdlttjrqh9jsgk2l8tgn6f0kxlfy98s3prz35z 100cro --from Default --chain-id crypto-org-chain-mainnet-1
```

#### Check Balance

```sh
chain-maind query bank balances cro1zdlttjrqh9jsgk2l8tgn6f0kxlfy98s3zwpck7
```

#### Create Validator

```sh
chain-maind tx staking create-validator [flags]
```

#### Unjail Validator

```sh
chain-maind tx slashing unjail --from node1 --chain-id crypto-org-chain-mainnet-1
```

For detailed information, visit the [Cronos POS Chain Docs](https://docs.cronos-pos.org/for-users/wallets/cli).
```

reference:
https://docs.cronos-pos.org/for-users/wallets/cli




https://docs.cronos-pos.org/for-users/wallets/mainnet-address-generation

```markdown
# Mainnet Address Generation

This document contains information on generating account addresses for the Cronos PoS Chain mainnet.

## Address Prefix

Mainnet addresses start with the prefix `cro`, e.g., `cro1y8ua5laceufhqtwzyhahq0qk7rm87hhugtsfey`.

## Hierarchical Deterministic Wallet (HD Wallet) Derivation Path

- Coin Type: 394
- Derivation Path: `44'/394'/0'/0/{index}`

## Generating an Address

### Important Notes

- **Backup your mnemonic words:** Ensure mnemonic words are safely backed up.
- **Secure Environment:** Generate addresses on a trusted, secure computer.
- **Verify:** Always verify mnemonic words and addresses.

### Methods to Generate Mainnet Address

1. **Release Binary (CLI)**
2. **Ledger Wallet**
3. **Programmatically via JavaScript Library**
4. **Crypto.com DeFi Desktop Wallet**

### A. Release Binary (CLI)

1. **Download Binary:**
   ```sh
   curl -LOJ https://github.com/crypto-org-chain/chain-main/releases/download/v3.3.9/chain-main_3.3.9_Linux_x86_64.tar.gz
   tar -zxvf chain-main_3.3.9_Linux_x86_64.tar.gz
   ```
2. **Create New Key and Address:**
   ```sh
   ./chain-maind keys add Default
   ```

### B. Ledger Wallet

1. **Install Ledger Application:**
   - Plug in Ledger device
   - Open Ledger Manager and install "Cronos PoS Chain"
2. **Generate Address via CLI:**
   ```sh
   ./chain-maind keys add myledger --ledger
   ```
3. **Verify Address:** Ensure the address displayed on Ledger matches the terminal output.

### C. Programmatically

Example in JavaScript:
```js
const sdk = require("@crypto-com/chain-jslib");
const HDKey = sdk.HDKey;
const randomHDKey = HDKey.generateMnemonic(24);
const privateKey = randomHDKey.derivePrivKey("m/44'/394'/0'/0/0");
const keyPair = sdk.Secp256k1KeyPair.fromPrivKey(privateKey);
const address = new sdk.CroSDK({ network: sdk.CroNetwork.Testnet }).Address(keyPair).account();
console.log(address);
```

### D. Crypto.com DeFi Desktop Wallet

1. **Download and Install:** Follow the [release page](https://github.com).
2. **Create Wallet:** Open the application, set up a password, create a wallet, and verify the mnemonic words.

For detailed steps and verification, visit the [Mainnet Address Generation documentation](https://docs.cronos-pos.org/for-users/wallets/mainnet-address-generation).
```

You can visit the full documentation [here](https://docs.cronos-pos.org/for-users/wallets/mainnet-address-generation).
```





## examples


chain-maind keys add matledger --ledger --keyring-backend test
chain-maind query bank balances <address>
chain-maind query bank balances <address> --chain-id crypto-org-chain-mainnet-1 --node https://cronos-pos-rpc.publicnode.com:443