



yourtokend query staking validators 

curl 'http://0.0.0.0:26657/validators?' | jq '.'



## How to find the  public key of the Tendermint consensus engine for that node

Question:
just before I create the validator on the second node, I need to find a public key on the second node with this:
 matluxchaind tendermint show-validator

What is exactly that public key? I thought the validator wasn't created yet...

Answer:
The public key obtained from running `matluxchaind tendermint show-validator` on the second node is the public key of the Tendermint consensus engine for that node. This key is generated when you initialize the node, and it is essential for associating the validator with the specific node that will participate in the consensus process.

### Understanding the Public Key

- **Tendermint Consensus Public Key**: Each node in a Cosmos SDK-based network that runs the Tendermint consensus engine has a unique public key. This public key is part of the node's identity in the consensus process.

- **Validator Creation**: When you create a validator, you link this Tendermint public key to the validator. This association is crucial because it tells the network which node will be responsible for proposing and validating blocks.

### Steps to Obtain and Use the Public Key

1. **Initialize the Second Node**: Ensure the second node is properly initialized and running. During initialization, the node will generate a public/private key pair for the consensus engine.

2. **Obtain the Tendermint Public Key**:
   On the second node, run:
   ```bash
   matluxchaind tendermint show-validator
   ```
   This command outputs the public key for the node's Tendermint consensus engine.

3. **Use the Public Key in Validator Creation**: When you create the validator, you include this public key in the transaction. This links the validator to the specific node that will participate in consensus.

### Example Process

#### 1. Initialize the Second Node
Ensure the node is initialized and running:
```bash
matluxchaind init <node-moniker> --chain-id <chain-id>
```

#### 2. Obtain the Tendermint Public Key
Run the following command on the second node:
```bash
matluxchaind tendermint show-validator
```
Example output:
```plaintext
cosmosvalconspub1zcjduepqsyllx0kszy8kpjl5p8dv7emxt6mf7v72y3pup6f68u63my5gl8jqph3e5r
```
This is the public key that identifies the node in the Tendermint consensus process.

#### 3. Create the Validator
Use this public key when creating the validator. Here’s an example command using a JSON file (`joiningvalidator.json`):

**JSON File (`joiningvalidator.json`):**
```json
{
    "pubkey": {"@type":"/cosmos.crypto.ed25519.PubKey","key":"qF2NBSvEMRlhARIM/hS0+W8ZyWsIpUF/D/GwO+aHh3A="},
    "amount": "1000000utctest",
    "moniker": "joiningvalidator",
    "commission-rate": "0.1",
    "commission-max-rate": "0.2",
    "commission-max-change-rate": "0.01",
    "min-self-delegation": "1"
}
```

**Validator Creation Command:**
```bash
matluxchaind tx staking create-validator ./joiningvalidator.json --from joiningvalidator --fees 50000utctest --chain-id utctestchain --keyring-backend test
```

### Summary

- **Public Key**: The public key obtained from `matluxchaind tendermint show-validator` represents the node's identity in the Tendermint consensus process.
- **Validator Association**: This key is used during validator creation to link the validator to the specific node.
- **Initialization**: The node must be initialized and running to generate and retrieve the public key.
- **Validation**: Including this public key in the validator creation process ensures that the correct node is used for consensus operations.

By following these steps, you can correctly associate your new validator with the second node, enabling it to participate in the network's consensus process.


## Why using --keyring-backend test in each commands?

The `--keyring-backend` flag specifies the keyring backend to use for managing private keys and accounts in the Cosmos SDK. The `test` backend is typically used for development and testing purposes because it provides a simple and convenient way to manage keys without requiring secure storage mechanisms that are necessary in a production environment.

### Keyring Backends

The Cosmos SDK supports different keyring backends, each suited for different use cases:

1. **`os`**: Uses the operating system's native key management system (e.g., macOS Keychain, Linux's Secret Service, Windows Credential Store). This is recommended for production environments because it provides better security.
2. **`file`**: Stores keys in an encrypted file. This backend is also suitable for production but requires proper management of the encryption keys.
3. **`test`**: Stores keys in-memory and is intended for development and testing. It does not provide any encryption or secure storage, so it's not suitable for production.
4. **`kwallet`**: Uses KDE Wallet for key management on Linux systems.
5. **`pass`**: Uses the `pass` command-line tool for key management on Unix-like systems.

### Using `--keyring-backend test`

When you use the `--keyring-backend test` flag, you are indicating that the keys should be managed in-memory for the duration of the session or process. This backend is easy to set up and use, making it ideal for testing and development scenarios where security is not a primary concern.

### Example Scenarios

1. **Key Generation**:
   ```bash
   utctestchaind keys add joiningvalidator --keyring-backend test
   ```
   This command creates a new key named `joiningvalidator` and stores it in the in-memory keyring. If you restart the node or session, the key will be lost.

2. **Querying Key Information**:
   ```bash
   utctestchaind keys show joiningvalidator --keyring-backend test -a
   ```
   This command retrieves the address associated with the `joiningvalidator` key from the in-memory keyring.

3. **Sending Tokens**:
   ```bash
   utctestchaind tx bank send cosmos1z2epxhjn6qrg0uca6j0rq7llupe3n0nllq6mxe cosmos1qrja75da6xdzgkerzwyaey7scwltras386ly6c 10000000utctest --fees 50000utctest --chain-id utctestchain --keyring-backend test --from myvalidator
   ```
   This command sends tokens from `myvalidator` to `joiningvalidator`, using keys stored in the in-memory keyring.

4. **Creating a Validator**:
   ```bash
   utctestchaind tx staking create-validator ./joiningvalidator.json --from joiningvalidator --fees 50000utctest --chain-id utctestchain --keyring-backend test
   ```
   This command creates a new validator using the `joiningvalidator` key stored in the in-memory keyring.

### Advantages of Using `test` Backend

- **Convenience**: Quick and easy to set up, ideal for development and testing environments.
- **No Persistent Storage**: Keys are stored in-memory and do not persist across sessions, which can be useful for testing scenarios where you want to start fresh each time.

### Disadvantages of Using `test` Backend

- **Lack of Security**: No encryption or secure storage, making it unsuitable for production environments.
- **Non-Persistence**: Keys are lost when the session or process ends, which means you need to recreate them each time.

### Summary

Using `--keyring-backend test` in each command indicates that you are working in a development or testing environment where ease of use and setup speed are prioritized over security. This backend is not suitable for production, where secure storage mechanisms provided by the `os` or `file` backends should be used.