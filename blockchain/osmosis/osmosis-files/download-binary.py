import os
import sys
import argparse
import subprocess
import platform
from enum import Enum

MAINNET_VERSION = "25.2.0"
TESTNET_VERSION = "25.2.0"

NETWORK_CHOICES = ['osmosis-1', 'osmo-test-5']

parser = argparse.ArgumentParser(description="Osmosis Installer")

parser.add_argument(
    '--network',
    type=str,
    choices=NETWORK_CHOICES,
    help=f"Network to join: {NETWORK_CHOICES})",
    required=True
)

parser.add_argument(
    "--binary_path",
    type=str,
    help=f"Path where to download the binary",
    default="/usr/local/bin"
)

parser.add_argument(
    '-o',
    '--overwrite',
    action='store_true',
    help="Overwrite existing Osmosis binary without prompt",
    dest="overwrite"
)

args = parser.parse_args()

class NetworkChoice(str, Enum):
    MAINNET = "1"
    TESTNET = "2"

class bcolors:
    OKGREEN = '\033[92m'
    RED = '\033[91m'
    ENDC = '\033[0m'
    PURPLE = '\033[95m'

TESTNET = {
    "chain_id": "osmo-test-5",
    "binary_url": {
        "linux": {
            "amd64": f"https://osmosis.fra1.digitaloceanspaces.com/binaries/v{TESTNET_VERSION}/osmosisd-{TESTNET_VERSION}-linux-amd64",
            "arm64": f"https://osmosis.fra1.digitaloceanspaces.com/binaries/v{TESTNET_VERSION}/osmosisd-{TESTNET_VERSION}-linux-arm64"
        },
        "darwin": {
            "amd64": f"https://osmosis.fra1.digitaloceanspaces.com/binaries/v{TESTNET_VERSION}/osmosisd-{TESTNET_VERSION}-darwin-amd64",
            "arm64": f"https://osmosis.fra1.digitaloceanspaces.com/binaries/v{TESTNET_VERSION}/osmosisd-{TESTNET_VERSION}-darwin-arm64"
        },
    }
}

MAINNET = {
    "chain_id": "osmosis-1",
    "binary_url": {
        "linux": {
            "amd64": f"https://osmosis.fra1.digitaloceanspaces.com/binaries/v{MAINNET_VERSION}/osmosisd-{MAINNET_VERSION}-linux-amd64",
            "arm64": f"https://osmosis.fra1.digitaloceanspaces.com/binaries/v{MAINNET_VERSION}/osmosisd-{MAINNET_VERSION}-linux-arm64"
        },
        "darwin": {
            "amd64": f"https://osmosis.fra1.digitaloceanspaces.com/binaries/v{MAINNET_VERSION}/osmosisd-{MAINNET_VERSION}-darwin-amd64",
            "arm64": f"https://osmosis.fra1.digitaloceanspaces.com/binaries/v{MAINNET_VERSION}/osmosisd-{MAINNET_VERSION}-darwin-arm64"
        },
    }
}

def download_binary(network):
    """
    Downloads the binary for the specified network based on the operating system and architecture.

    Args:
        network (NetworkChoice): The network type, either MAINNET or TESTNET.

    Raises:
        SystemExit: If the binary download URL is not available for the current operating system and architecture.
    """
    binary_path = os.path.join(args.binary_path, "osmosisd")

    if not args.overwrite:
        # Check if osmosisd is already installed
        try:
            subprocess.run([binary_path, "version"], check=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
            print("osmosisd is already installed at " + bcolors.OKGREEN + f"{binary_path}" + bcolors.ENDC)
            while True:
                choice = input("Do you want to skip the download or overwrite the binary? (skip/overwrite): ").strip().lower()
                if choice == "skip":
                    print("Skipping download.")
                    return
                elif choice == "overwrite":
                    print("Proceeding with overwrite.")
                    break
                else:
                    print("Invalid input. Please enter 'skip' or 'overwrite'.")
        except FileNotFoundError:
            print("osmosisd is not installed. Proceeding with download.")

    operating_system = platform.system().lower()
    architecture = platform.machine()

    if architecture == "x86_64":
        architecture = "amd64"
    elif architecture == "aarch64":
        architecture = "arm64"

    if architecture not in ["arm64", "amd64"]:
        print(f"Unsupported architecture {architecture}.")
        sys.exit(1)

    if network == NetworkChoice.TESTNET:
        binary_urls = TESTNET["binary_url"]
    else:
        binary_urls = MAINNET["binary_url"]

    if operating_system in binary_urls and architecture in binary_urls[operating_system]:
        binary_url = binary_urls[operating_system][architecture]
    else:
        print(f"Binary download URL not available for {operating_system}/{architecture}")
        sys.exit(0)

    try:
        print("Downloading " + bcolors.PURPLE + "osmosisd" + bcolors.ENDC, end="\n\n")
        print("from " + bcolors.OKGREEN + f"{binary_url}" + bcolors.ENDC, end=" ")
        print("to " + bcolors.OKGREEN + f"{binary_path}" + bcolors.ENDC)
        print()
        print(bcolors.OKGREEN + "💡 You can change the path using --binary_path" + bcolors.ENDC)

        subprocess.run(["wget", binary_url, "-q", "-O", "/tmp/osmosisd"], check=True)
        os.chmod("/tmp/osmosisd", 0o755)

        if platform.system() == "Linux":
            subprocess.run(["sudo", "mv", "/tmp/osmosisd", binary_path], check=True)
            subprocess.run(["sudo", "chown", f"{os.environ['USER']}:{os.environ['USER']}", binary_path], check=True)
            subprocess.run(["sudo", "chmod", "+x", binary_path], check=True)
        else:
            subprocess.run(["mv", "/tmp/osmosisd", binary_path], check=True)

        # Test binary
        subprocess.run(["osmosisd", "version"], check=True)

        print("Binary downloaded successfully.")

    except subprocess.CalledProcessError as e:
        print(e)
        print("Failed to download the binary.")
        sys.exit(1)

def main():
    if args.network == "osmosis-1":
        network = NetworkChoice.MAINNET
    else:
        network = NetworkChoice.TESTNET

    download_binary(network)

main()
