Testing a GitHub Action locally before pushing it to GitHub can be achieved using a tool like **`act`**. The `act` tool allows you to run GitHub Actions locally in a containerized environment, mimicking the GitHub Actions runner.

### Steps to test GitHub Actions locally using `act`:

### 1. **Install `act`**
   - First, you need to install `act` on your local machine. You can install it via Homebrew (on macOS and Linux), or download the appropriate binary for your OS.

   #### On macOS or Linux using Homebrew:
   ```bash
   brew install act
   ```

   #### Alternatively, you can install it using a script or binary directly:
   Follow the instructions at the official repository: [https://github.com/nektos/act](https://github.com/nektos/act)

### 2. **Configure Secrets Locally**
   - GitHub Actions often use secrets that are configured in the repository. You will need to set up those secrets locally for `act` to use.

   Create a `.secrets` file in the root of your repository to store secrets. Here is an example format:

   ```bash
   OVH_S3_APPLICATION_KEY=your-key
   OVH_S3_APPLICATION_SECRET=your-secret
   OVH_KUBECONFIG=your-kubeconfig
   ```

   You can also pass secrets as command-line arguments if you don't want to store them in a file:

   ```bash
   act -s OVH_S3_APPLICATION_KEY=your-key -s OVH_S3_APPLICATION_SECRET=your-secret -s OVH_KUBECONFIG=your-kubeconfig
   ```

### 3. **Run Your Workflow Locally**
   After installing `act` and configuring your secrets, you can run your workflow locally. Use the following steps:

   #### Run all workflows:
   ```bash
   act
   ```

   #### Run a specific workflow:
   ```bash
   act -j <job-name>
   ```

   Replace `<job-name>` with the specific job you want to run (which corresponds to the `id` of a job in your GitHub Actions workflow YAML).

   #### Specify an event:
   You can also specify an event that triggers the workflow:

   ```bash
   act <event>
   ```

   For example, to run a workflow triggered by a `push` event:
   ```bash
   act push
   ```

### 4. **Use Docker to Simulate GitHub Runner Environments**
   The `act` tool uses Docker to run workflows locally. By default, `act` uses a very minimal image (`nektos/act-environments-ubuntu:18.04`). You can run with the larger GitHub-provided runner environments by using the `--platform` flag.

   For example:
   ```bash
   act --platform ubuntu-latest=nektos/act-environments-ubuntu:22.04
   ```

### 5. **View Logs and Debugging**
   - The logs will be printed to your terminal, just like they would in GitHub Actions.
   - You can see any errors or output from the actions and adjust your workflow accordingly.

### Example of Running a Workflow Locally
Assume you have a GitHub Actions workflow in `.github/workflows/cleanup.yml`. You can run it locally with:

```bash
act workflow_dispatch
```

If your workflow requires secrets, make sure they are set up either in the `.secrets` file or passed as parameters, like this:

```bash
act -s OVH_S3_APPLICATION_KEY=your-key -s OVH_S3_APPLICATION_SECRET=your-secret -s OVH_KUBECONFIG=your-kubeconfig workflow_dispatch
```

### Additional Notes
- **Docker Installation**: Ensure Docker is installed on your machine since `act` uses Docker to run the workflows.
- **Runner Versions**: You can choose specific runner versions (like `ubuntu-latest` or `ubuntu-20.04`) using `--platform`.

By using `act`, you can thoroughly test your GitHub Actions workflows locally before committing and pushing them to GitHub.