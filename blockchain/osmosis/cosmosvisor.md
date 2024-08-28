


Cosmovisor is a process manager for Cosmos SDK applications that helps automate the upgrade process for blockchains. It is particularly useful for managing binary upgrades without manual intervention, ensuring smooth transitions during upgrades. Here’s a breakdown of how Cosmovisor works and how it fits into the installation and operation of a Cosmos SDK-based blockchain node like Osmosis.

### How Cosmovisor Works

1. **Installation and Setup**:
   - Cosmovisor is typically installed and configured during the setup of a Cosmos SDK-based blockchain node.
   - It relies on specific environment variables to determine paths for the current binary, upgrade binaries, and configuration files.

2. **Binary Management**:
   - Cosmovisor manages multiple versions of the node binary. It ensures that the node runs the correct version based on the chain's upgrade requirements.
   - The binaries are organized in directories named after the respective versions under the `upgrades` directory.

3. **Environment Variables**:
   - **`DAEMON_HOME`**: Specifies the home directory for the daemon, typically where the blockchain’s configuration files and data are stored.
   - **`DAEMON_NAME`**: The name of the daemon binary (e.g., `osmosisd`).
   - **`DAEMON_ALLOW_DOWNLOAD_BINARIES`**: If set to `true`, allows Cosmovisor to download binaries if not found locally.
   - **`DAEMON_RESTART_AFTER_UPGRADE`**: If set to `true`, Cosmovisor restarts the daemon after a successful upgrade.

4. **Upgrade Process**:
   - **Pre-Upgrade**: Before an upgrade, the new binary should be placed in the appropriate directory (e.g., `$DAEMON_HOME/cosmovisor/upgrades/<upgrade-name>/bin`).
   - **During Upgrade**: When the blockchain signals an upgrade (via a governance proposal or consensus state), Cosmovisor detects the signal and gracefully stops the running daemon.
   - **Post-Upgrade**: Cosmovisor replaces the current binary with the new one and restarts the node using the new binary.

5. **Automation**:
   - Cosmovisor automatically handles the stop, replacement, and restart processes. This reduces downtime and the risk of human error during manual upgrades.

### How to Continue Using Cosmovisor After Compiling `osmosisd`

After compiling `osmosisd`, you need to configure and set up Cosmovisor to manage your node. Here are the steps:

1. **Install Cosmovisor**:
   If you haven't installed Cosmovisor yet, you can do so by downloading it from the official Cosmos SDK GitHub repository or by building it from source.

   ```bash
   go install github.com/cosmos/cosmos-sdk/cosmovisor/cmd/cosmovisor@latest
   ```

2. **Set Up Environment Variables**:
   Create a `.env` file or set environment variables directly in your shell profile (`.bashrc`, `.zshrc`).

   ```bash
   export DAEMON_NAME=osmosisd
   export DAEMON_HOME=$HOME/.osmosisd
   export DAEMON_ALLOW_DOWNLOAD_BINARIES=true
   export DAEMON_RESTART_AFTER_UPGRADE=true
   ```

3. **Directory Structure**:
   Ensure the directory structure for Cosmovisor is set up correctly:

   ```bash
   mkdir -p $DAEMON_HOME/cosmovisor/genesis/bin
   mkdir -p $DAEMON_HOME/cosmovisor/upgrades/<upgrade-name>/bin
   ```

   Place the compiled `osmosisd` binary in the `genesis/bin` directory:

   ```bash
   cp /path/to/compiled/osmosisd $DAEMON_HOME/cosmovisor/genesis/bin/osmosisd
   ```

4. **Run Cosmovisor**:
   Start the node using Cosmovisor:

   ```bash
   cosmovisor start
   ```

### Monitoring and Managing Cosmovisor

- **Logs**: Monitor the logs to ensure that Cosmovisor is running correctly and managing upgrades as expected.
  
  ```bash
  journalctl -u cosmovisor -f
  ```

- **Upgrades**: When an upgrade is scheduled, place the new binary in the appropriate upgrade directory before the upgrade block height is reached.

By following these steps, you ensure that Cosmovisor is properly set up to manage your Osmosis node, handling upgrades smoothly and minimizing downtime.