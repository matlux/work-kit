



yourtokend query staking validators 

curl 'http://0.0.0.0:26657/validators?' | jq '.'



## How to find the  public key of the Tendermint consensus engine for that node

Question:
just before I create the validator on the second node, I need to find a public key on the second node with this:
 minid tendermint show-validator

What is exactly that public key? I thought the validator wasn't created yet...

Answer:
The public key obtained from running `minid tendermint show-validator` on the second node is the public key of the Tendermint consensus engine for that node. This key is generated when you initialize the node, and it is essential for associating the validator with the specific node that will participate in the consensus process.

### Understanding the Public Key

- **Tendermint Consensus Public Key**: Each node in a Cosmos SDK-based network that runs the Tendermint consensus engine has a unique public key. This public key is part of the node's identity in the consensus process.

- **Validator Creation**: When you create a validator, you link this Tendermint public key to the validator. This association is crucial because it tells the network which node will be responsible for proposing and validating blocks.

### Steps to Obtain and Use the Public Key

1. **Initialize the Second Node**: Ensure the second node is properly initialized and running. During initialization, the node will generate a public/private key pair for the consensus engine.

2. **Obtain the Tendermint Public Key**:
   On the second node, run:
   ```bash
   minid tendermint show-validator
   ```
   This command outputs the public key for the node's Tendermint consensus engine.

3. **Use the Public Key in Validator Creation**: When you create the validator, you include this public key in the transaction. This links the validator to the specific node that will participate in consensus.

### Example Process

#### 1. Initialize the Second Node
Ensure the node is initialized and running:
```bash
minid init <node-moniker> --chain-id <chain-id>
```

#### 2. Obtain the Tendermint Public Key
Run the following command on the second node:
```bash
minid tendermint show-validator
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
minid tx staking create-validator ./joiningvalidator.json --from joiningvalidator --fees 50000utctest --chain-id utctestchain --keyring-backend test
```

### Summary

- **Public Key**: The public key obtained from `minid tendermint show-validator` represents the node's identity in the Tendermint consensus process.
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
   minid keys add joiningvalidator --keyring-backend test
   ```
   This command creates a new key named `joiningvalidator` and stores it in the in-memory keyring. If you restart the node or session, the key will be lost.

2. **Querying Key Information**:
   ```bash
   minid keys show joiningvalidator --keyring-backend test -a
   ```
   This command retrieves the address associated with the `joiningvalidator` key from the in-memory keyring.

3. **Sending Tokens**:
   ```bash
   minid tx bank send cosmos1z2epxhjn6qrg0uca6j0rq7llupe3n0nllq6mxe cosmos1qrja75da6xdzgkerzwyaey7scwltras386ly6c 10000000utctest --fees 50000utctest --chain-id utctestchain --keyring-backend test --from myvalidator
   ```
   This command sends tokens from `myvalidator` to `joiningvalidator`, using keys stored in the in-memory keyring.

4. **Creating a Validator**:
   ```bash
   minid tx staking create-validator ./joiningvalidator.json --from joiningvalidator --fees 50000utctest --chain-id utctestchain --keyring-backend test
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


## Does a binaryd export command be used as a new genesis ?

Questions: Can I use minid export output as a new genesis file to bootstrap another chain-id with?
Does it act as a compaction of the blockchain when upgrade to the keepers are needed?


Yes, you can use the `minid export` output as a new genesis file to bootstrap another chain with a different chain ID. This process is often used when upgrading a blockchain, performing state migrations, or setting up a new chain from an existing state.

### Using `minid export` for Bootstrapping a New Chain

1. **Exporting the State**:
   - When you run the command:
     ```bash
     minid export
     ```
   - This command exports the current state of the blockchain into a JSON format that resembles a genesis file. This exported state includes all the accounts, balances, staking information, module states, and other critical data that represent the current state of the blockchain.

2. **Creating a New Genesis File**:
   - The output from `minid export` can be saved as a new genesis file (`genesis.json`). This genesis file can then be used to initialize a new chain:
     ```bash
     minid init new-chain --chain-id <new-chain-id>
     ```
   - After initialization, replace the newly created genesis file with the exported one:
     ```bash
     cp exported_genesis.json ~/.minid/config/genesis.json
     ```
   - Update the chain ID in the new genesis file to match the desired new chain ID:
     ```json
     {
       "chain_id": "<new-chain-id>",
       ...
     }
     ```
   - Start the new chain with the updated genesis file:
     ```bash
     minid start
     ```

3. **Bootstrapping a New Chain**:
   - By using the modified genesis file, you effectively bootstrap a new blockchain that starts from the state of the previous chain. This new chain can have a different chain ID and can undergo changes such as upgraded keepers, modules, or parameters.

### Acting as a Compaction of the Blockchain

When upgrading to new versions of keepers or performing major state migrations, the `export` and re-import process acts similarly to a compaction of the blockchain:

1. **State Compaction**:
   - **Old State to Genesis**: The `export` process extracts the current application state, effectively summarizing the blockchain's state without the full history of transactions.
   - **Genesis Initialization**: When this exported state is used as a new genesis file, the new chain starts with only the latest state, dropping the historical blocks and reducing the overall storage size.

2. **Upgrading Keepers**:
   - If the new version of your application introduces changes in the data structures or logic of keepers, using the export and import method allows for a clean state initialization with the updated keepers without needing a hard fork.
   - This approach is often cleaner than in-place migrations, as it avoids complex upgrade logic embedded within the modules.

3. **Practical Steps for Upgrades**:
   - **Export the current state** using `minid export`.
   - **Modify the exported genesis** for any required parameter changes, including updating the chain ID, if needed.
   - **Update software** to the new version that includes the upgraded keepers or module changes.
   - **Initialize the new chain** with the updated genesis file, effectively starting a new chain from the latest state of the previous chain.

### Key Considerations

- **Consensus**: Ensure that validators and other participants are aligned on the new genesis file and chain ID to avoid splits.
- **Governance**: If applicable, ensure that the transition is agreed upon via governance mechanisms to maintain consensus among stakeholders.
- **Testing**: Test the new genesis and upgraded software in a staging environment to ensure compatibility and correctness before deploying it on the mainnet.

By using this process, you effectively restart the blockchain with a "compacted" state, without the burden of historical data, making it suitable for upgrades, state migrations, and launching new chains.



## is it better to start building my own blockchain with ignite or with chain-minimal ?




### **Ignite**
**Pros:**
- **Full-Featured Framework:** Ignite (formerly known as Starport) provides a complete framework for building blockchains with the Cosmos SDK. It includes a wide range of tools and utilities to accelerate development, from scaffolding to deploying your blockchain.
- **Ease of Use:** Ignite streamlines many of the complexities associated with blockchain development by automating setup, code generation, and deployment processes.
- **Community and Support:** As part of the Cosmos ecosystem, Ignite benefits from strong community support, extensive documentation, and a vibrant ecosystem of developers and projects.
- **Modular and Extensible:** Ignite allows you to leverage the modularity of the Cosmos SDK, where you can add or customize modules based on your needs, making it flexible and adaptable.

**Cons:**
- **Overhead for Small Projects:** For very basic or highly experimental blockchains, Ignite might introduce more overhead than necessary due to its comprehensive nature.
- **Learning Curve:** Although it simplifies many processes, there's still a learning curve associated with mastering Ignite and the underlying Cosmos SDK, especially for newcomers.

### **Chain-Minimal**

as in https://github.com/cosmosregistry/chain-minimal

**Pros:**
- **Lightweight and Minimal:** As the name suggests, `chain-minimal` is a stripped-down example of a Cosmos SDK blockchain. It provides a bare-bones starting point, which can be perfect if you want to understand the essentials without unnecessary extras.
- **Good for Learning:** If your goal is to learn the Cosmos SDK fundamentals or to create a highly customized, lean blockchain, starting with `chain-minimal` can provide a clearer view of the core components.
- **Less Complexity:** For developers looking to maintain full control over their blockchain’s design and operation with minimal dependencies, `chain-minimal` offers a straightforward approach.

**Cons:**
- **Less Tooling and Automation:** Compared to Ignite, `chain-minimal` does not offer the same level of scaffolding, tooling, or automation, which means more manual setup and configuration.
- **Limited Features Out-of-the-Box:** Since it's a minimal example, it lacks many advanced features and integrations you might need for production-grade blockchains, requiring more manual coding.

### **Conclusion:**
- **Use Ignite** if you’re looking to quickly build and deploy a blockchain with all the benefits of a comprehensive framework. Ignite is ideal for projects where speed, ease of development, and integration with the Cosmos ecosystem are priorities.

- **Use Chain-Minimal** if your focus is on understanding the fundamentals of the Cosmos SDK, creating a highly custom or experimental blockchain, or starting from the absolute basics without additional overhead. It’s best for developers who want a hands-on, from-scratch approach to learning and building.

## The cosmos sdk documentation seems to rely a lot on simapp. What is simapp? How does it differ from the app that ignite can scaffold ?

Ultimately, if you value speed and convenience, Ignite is likely the better choice. If you prefer a minimalistic, hands-on approach to fully grasp the underlying mechanics, then starting with `chain-minimal` could be more rewarding.



### What is Simapp?

Simapp is a simple application provided as part of the Cosmos SDK. It's a reference implementation that showcases how to build a Cosmos SDK-based blockchain application. Simapp serves as a foundational example for developers to understand the basic structure and functionality of a Cosmos SDK application. It includes the core modules and functionalities necessary to run a minimal blockchain, and it's often used for testing, development, and educational purposes.

### Features of Simapp:

1. **Basic Modules**: Includes essential modules like bank, staking, governance, and others to demonstrate basic blockchain operations.
2. **Test Environment**: Provides a testing environment to simulate blockchain operations without deploying a full network.
3. **Reference Implementation**: Acts as a guide for developers to understand the integration and interaction of different modules within the Cosmos SDK.

### How Simapp Differs from an App Scaffolded by Ignite

Ignite (formerly known as Starport) is a developer tool for scaffolding, building, and launching a Cosmos SDK-based blockchain. It provides an automated way to create a new blockchain application with customized modules and configurations. Here are some key differences between Simapp and an app scaffolded by Ignite:

1. **Customization**:
   - **Simapp**: Comes with predefined modules and configurations, making it a standard example with limited customization.
   - **Ignite**: Allows developers to scaffold an application with customizable modules, parameters, and configurations tailored to specific use cases.

2. **Purpose**:
   - **Simapp**: Primarily serves as a learning tool and a reference implementation for understanding the Cosmos SDK.
   - **Ignite**: Designed to kickstart the development of new blockchain applications, providing a foundation that developers can build upon and extend.

3. **Development Workflow**:
   - **Simapp**: Requires manual modifications and integrations to extend functionality beyond the provided example.
   - **Ignite**: Automates the initial setup and configuration, generating boilerplate code and providing tools for rapid development and iteration.

4. **Flexibility**:
   - **Simapp**: Limited to the predefined modules and functionalities included in the reference implementation.
   - **Ignite**: Offers greater flexibility by allowing developers to define custom modules, messages, and handlers, making it suitable for a wide range of blockchain applications.

5. **Community and Support**:
   - **Simapp**: Primarily maintained by the Cosmos SDK team as a part of the SDK itself.
   - **Ignite**: Supported by a dedicated community and team focused on improving the development experience and providing additional features and tools.

### Summary

While Simapp is a valuable tool for understanding the basics of the Cosmos SDK and experimenting with blockchain functionalities, Ignite provides a more robust and flexible solution for developers looking to create custom blockchain applications. Ignite streamlines the development process, offering customization and automation features that go beyond the capabilities of Simapp.



## How to find how much is staked on a given validator?

```
mychaind query staking delegations-to $(mychaind keys show validator2 --keyring-backend test --bech val --address) --chain-id utc-test-1 --output json
```

## What is the difference between the validator Account and a Validator Operator Account? How do you query it


Query validator Account
```bash
mychaind keys show validator2 --keyring-backend test --address
```

This command displays the **account address** of the key named `validator2` in your keyring. The output:

```
cosmos19ywhwwycdunw9yluwsefxe2ehs2d6nnurzv4h9
```

is the **account address** (usually starting with `cosmos...`), not the validator operator address (which starts with `cosmosvaloper...`). 

### Difference Between Account Address and Validator Address

- **Account Address (`cosmos...`)**: This is the standard address used for holding funds and is associated with an individual's or entity's account.
- **Validator Operator Address (`cosmosvaloper...`)**: This is the address specifically for validator operations, used when creating a validator or querying validator-specific information.

### Finding the Validator Operator Address

To find the validator operator address associated with your account address (`cosmos...`), you can use the following command:

```bash
utctestchaind keys show validator2 --keyring-backend test --bech val --address
```

This will return the validator operator address, which is what you need for querying delegations to your validator:

```
cosmosvaloper19ywhwwycdunw9yluwsefxe2ehs2d6nnurzv4h9
```

### Using the Validator Operator Address

Once you have the validator operator address, you can use it for queries like:

```bash
utctestchaind query staking delegations-to cosmosvaloper19ywhwwycdunw9yluwsefxe2ehs2d6nnurzv4h9 --chain-id <your-chain-id> --output json
```

This will show all delegations to your validator, helping you track where tokens are delegated on your validator.




## I created a validator with amount 900000000 , and I see field shares with 27 zeros with a similar value several order of magnitude larger, what does this shares attribute? What is its purpose?

root@utc-persistent-node-3-deployment-c746f76d9-lrzzw:/# mychaind query staking delegations-to cosmosvaloper16j9qracmadh977cp0zq2mam48zuyk7ql7g0tkq --chain-id utc-test-1 --output json
{
  "delegation_responses": [
    {
      "delegation": {
        "delegator_address": "cosmos16j9qracmadh977cp0zq2mam48zuyk7qlmum76n",
        "validator_address": "cosmosvaloper16j9qracmadh977cp0zq2mam48zuyk7ql7g0tkq",
        "shares": "900000000000000000000000000"
      },
      "balance": {
        "denom": "uvna",
        "amount": "900000000"
      }
    }
  ],
  "pagination": {
    "total": "1"
  }
}



The value of "shares" in the staking module of the Cosmos SDK represents a proportional amount of the total stake in a validator. Here's what it means and how it is calculated:
1. **Initial Delegation and Shares Calculation**:
   - When you delegate 900,000,000 `uvna`, the amount you delegate is converted into "shares" of the validator's total stake.
   - The "shares" are calculated with high precision to handle staking rewards, commission, and any changes in the validator's total stake.
2. **Representation of Shares**:
   - In your case, the "shares" value is `900000000000000000000000000`, which means 900 million (the amount you delegated) has been represented with 27 zeros, or in other words, 900 million multiplied by \(10^{18}\) (the SDK uses a high precision factor to handle staking calculations, often called `sdk.Dec` which uses 18 decimal places).
   - This large number is used internally to allow for precise calculation of staking rewards and slashing.
3. **How the Shares are Used**:
   - These shares determine your proportion of the total validator's stake. When rewards or penalties are applied, they are calculated based on the shares each delegator has in the validator.
   - This high precision ensures that even very small rewards or slashing events can be accurately accounted for.
To summarize, the large "shares" number is normal and is a result of the Cosmos SDK's design to handle staking operations with high precision. Your 900 million delegation was converted into shares using this high precision calculation, which is why you see a number with 27 zeros.


# How to use the Ignite explorer

## run this command to install the app
ignite app install -g github.com/ignite/apps/explorer
## run this command to start the explorer
ignite explorer gex --rpc-address http://localhost:26657