#!/bin/bash
set -e
RED='\033[0;31m'
GREEN='\033[0;32m'
CLEAN='\033[0m'

INSTALLATION=$1
if [ -z $INSTALLATION ]; then
    INSTALLATION=cosmovisor
fi

MONIKER=$2
if [ -z $MONIKER ]; then
    MONIKER=my-node
fi

OSMOSIS_HOME=$HOME/.osmosisd
OSMOSIS_VERSION=25.2.0
GOLANG_VERSION=1.22.3

ADDRBOOK_URL=https://rpc.testnet.osmosis.zone/addrbook
GENESIS_URL=https://osmosis.fra1.digitaloceanspaces.com/osmo-test-5/genesis.json
SNAPSHOT_URL=$(curl -s https://osmosis.fra1.digitaloceanspaces.com/osmo-test-5/snapshots/latest)

WAIT=10

# Ensure necessary directories exist
mkdir -p $OSMOSIS_HOME
mkdir -p $OSMOSIS_HOME/cosmovisor
mkdir -p $OSMOSIS_HOME/cosmovisor/genesis
mkdir -p $OSMOSIS_HOME/cosmovisor/genesis/bin
mkdir -p $OSMOSIS_HOME/cosmovisor/upgrades

# Copy the osmosisd binary to the cosmovisor genesis directory
cp /usr/local/bin/osmosisd $OSMOSIS_HOME/cosmovisor/genesis/bin/osmosisd

# Initialize osmosis home osmosisd init matlux-osmosis-node --home /root/.osmosisd
osmosisd init $MONIKER --home $OSMOSIS_HOME  --chain-id=osmo-test-5 > /dev/null 2>&1

# Update the config for RPC
dasel put string -f $OSMOSIS_HOME/config/config.toml '.rpc.laddr' "tcp://0.0.0.0:26657"

# Download genesis and addrbook
wget -q $GENESIS_URL -O $OSMOSIS_HOME/config/genesis.json
wget -q $ADDRBOOK_URL -O $OSMOSIS_HOME/config/addrbook.json

# wget $SNAPSHOT_URL
# wget -q -O - $SNAPSHOT_URL | lz4 -d | tar -C $OSMOSIS_HOME/ -xvf -
lz4 -d /root/snapshots/osmosis-snapshot-202407201816-10436772.tar.lz4 | tar -C $OSMOSIS_HOME/ -xvf -


# Create an empty upgrade-info.json if it doesn't exist
if [ ! -f $OSMOSIS_HOME/cosmovisor/genesis/upgrade-info.json ]; then
    mkdir -p $OSMOSIS_HOME/cosmovisor/genesis
    echo '{}' > $OSMOSIS_HOME/cosmovisor/genesis/upgrade-info.json
fi

printf "${GREEN}Node initialization and configuration completed. ${CLEAN}\n"

