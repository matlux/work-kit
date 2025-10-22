

To build a Docker image from a Dockerfile named `Dockerfile.mathieu`, you can use the following command:

```sh
docker build -t osmosis-node -f Dockerfile.mathieu .
```

Here's a breakdown of the command:

- `docker build` is the command to build a Docker image.
- `-t osmosis-node` tags the image with the name `osmosis-node`. You can replace `osmosis-node` with any name you prefer.
- `-f Dockerfile.mathieu` specifies the name of the Dockerfile.
- `.` indicates the build context, which is the current directory. Ensure you run this command in the directory where `Dockerfile.mathieu` is located.



To run the Docker image locally as a container, you can use the `docker run` command. Here are the steps to do this:

1. **Build the Docker image** (if not already done):
   ```sh
   docker build -t osmosis-node -f Dockerfile.mathieu .
   ```



2. **Run the Docker image interactivelly**:
   ```sh
   docker run -d --name osmosis-node-container -p 26656:26656 -p 26657:26657 osmosis-node
   ```


3. **Run the Docker image as a deamon**:
   ```sh
   docker run -d --name osmosis-node-container -p 26656:26656 -p 26657:26657 osmosis-node
   ```

Here's a breakdown of the `docker run` command:

- `-d` runs the container in detached mode (in the background).
- `--name osmosis-node-container` gives the container a name for easier management.
- `-p 26656:26656 -p 26657:26657` maps the container ports to the host ports, allowing you to access the service from your host machine.
- `osmosis-node` is the name of the image you built.

To check the status of the running container, you can use:

```sh
docker ps
```

To view logs from the container:

```sh
docker logs osmosis-node-container
```

To enter the container’s shell, you can use:

```sh
docker exec -it osmosis-node-container /bin/bash
```

To stop the container:

```sh
docker stop osmosis-node-container
```

To remove the container:

```sh
docker rm -f osmosis-node-container
```

These commands will help you manage the Docker container running the Osmosis node locally.