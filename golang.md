
## test project from commandline

go to project directory and run

    go test ./...




## How does go manages dependencies

Go uses the module system for managing imports and dependencies, and it often uses URLs that resemble GitHub repository paths to fetch libraries. Here’s a detailed explanation of how Go manages imports and dependencies, and how it fetches them:

### How Go Manages Imports and Dependencies

1. **Go Modules**:
   Go modules are the standard way to manage dependencies in Go. A Go module is defined by a `go.mod` file, which specifies the module’s path and its dependencies.

   ```go
   module github.com/yourusername/mychain

   go 1.18

   require (
       github.com/cosmos/cosmos-sdk v0.42.4
       // other dependencies
   )
   ```

2. **Module Path**:
   The module path is typically the URL of the repository where the module's source code is hosted. This is why you see URLs like `github.com/cosmos/cosmos-sdk` in Go import paths. When you import a package, you use its module path:

   ```go
   import "github.com/cosmos/cosmos-sdk/types"
   ```

3. **Fetching Dependencies**:
   When you run commands like `go build`, `go test`, or `go mod tidy`, Go will fetch the dependencies specified in the `go.mod` file. It uses the module path to locate the repository, usually on GitHub or other version control hosting services.

4. **Versioning**:
   The `go.mod` file also specifies the versions of the dependencies. Go uses semantic versioning to manage this. You can update the dependencies to their latest versions using `go get`:

   ```sh
   go get github.com/cosmos/cosmos-sdk@latest
   ```

5. **Dependency Resolution**:
   Go resolves dependencies by downloading the specified versions from their repositories and caching them locally. This happens automatically when you run `go build` or other related commands.

### Default Branch for Fetching

- **Main/Default Branch**: When fetching dependencies, Go will use the specified version in the `go.mod` file. If you specify a version tag, branch, or commit, Go will fetch that specific version. If no version is specified and you run a command like `go get github.com/user/repo`, Go will fetch the latest commit on the default branch (usually `main` or `master`).

- **Version Tags**: If you specify a version tag in the `go.mod` file, Go will fetch that specific version.
  
  ```go
  require github.com/cosmos/cosmos-sdk v0.42.4
  ```

- **Branches and Commits**: You can also specify a branch or a specific commit.
  
  ```sh
  go get github.com/user/repo@branchname
  go get github.com/user/repo@commit-hash
  ```

### Example

Here’s an example workflow to illustrate how Go fetches dependencies:

1. **Initialize a Module**:
   ```sh
   go mod init github.com/yourusername/myproject
   ```

2. **Add Dependencies**:
   Add dependencies to your `go.mod` file either manually or by importing them in your code and running `go mod tidy`.

   ```go
   require github.com/cosmos/cosmos-sdk v0.42.4
   ```

3. **Fetch Dependencies**:
   Run `go build` or `go mod tidy` to fetch the dependencies. Go will download the specified versions and store them in the module cache.

   ```sh
   go build
   ```

4. **Update Dependencies**:
   To update a dependency to its latest version, use `go get`:
   
   ```sh
   go get github.com/cosmos/cosmos-sdk@latest
   ```

### Local Cache

Go modules are cached locally in the `GOPATH/pkg/mod` directory. When you import a module, Go checks the local cache first before fetching from the network.

### Conclusion

Go’s module system simplifies dependency management by using URLs that resemble repository paths, allowing it to fetch code directly from version control systems like GitHub. It relies on the `go.mod` file to manage versions and ensure that your project builds consistently across different environments. By understanding how to use the `go.mod` file and the `go` command, you can effectively manage your project's dependencies.

## How to To install `gvm` (Go Version Manager) on macOS,


To install `gvm` (Go Version Manager) on macOS, you cannot use Homebrew directly as `gvm` is not available through Homebrew. Instead, you can install `gvm` using its installation script. Here are the steps to install `gvm` on macOS:

### Installing `gvm` on macOS

1. **Install Dependencies:**
   First, make sure you have the required dependencies installed. You might need to install some developer tools and Homebrew if you haven't already:
   ```sh
   xcode-select --install  # Install Xcode Command Line Tools
   /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"  # Install Homebrew if not already installed
   brew install bison
   ```

2. **Install `gvm`:**
   Use the following command to install `gvm`:
   ```sh
   bash < <(curl -sSL https://raw.githubusercontent.com/moovweb/gvm/master/binscripts/gvm-installer)
   ```

3. **Configure Your Shell:**
   Add `gvm` to your shell configuration file (`~/.bashrc`, `~/.zshrc`, etc.):
   ```sh
   echo "source ~/.gvm/scripts/gvm" >> ~/.bashrc
   source ~/.gvm/scripts/gvm
   ```

4. **Reload Your Shell:**
   Apply the changes to your current shell session:
   ```sh
   source ~/.gvm/scripts/gvm
   ```

5. **Install Go Versions Using `gvm`:**
   Now you can list and install Go versions using `gvm`. For example:
   ```sh
   gvm listall  # List all available Go versions
   gvm install go1.17.5  # Install a specific Go version
   gvm use go1.17.5 --default  # Set the default Go version
   ```

6. **Verify Installation:**
   Check the currently active Go version to ensure everything is set up correctly:
   ```sh
   go version
   ```

### Complete Example:

Here's a complete example script that you can run step-by-step to install `gvm` and set it up on macOS:

```sh
# Step 1: Install dependencies
xcode-select --install  # Install Xcode Command Line Tools
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"  # Install Homebrew
brew install bison

# Step 2: Install gvm
bash < <(curl -sSL https://raw.githubusercontent.com/moovweb/gvm/master/binscripts/gvm-installer)

# Step 3: Configure your shell
echo "source ~/.gvm/scripts/gvm" >> ~/.bashrc  # For bash
echo "source ~/.gvm/scripts/gvm" >> ~/.zshrc   # For zsh

# Step 4: Reload your shell
source ~/.bashrc  # or source ~/.zshrc

# Step 5: Install Go versions using gvm
gvm listall  # List all available Go versions
gvm install go1.17.5  # Install a specific Go version
gvm use go1.17.5 --default  # Set the default Go version

# Step 6: Verify installation
go version
```

This should set up `gvm` on your macOS system, allowing you to manage multiple Go versions easily. If you encounter any issues, make sure that you have all the necessary dependencies installed and your shell configuration is correct.