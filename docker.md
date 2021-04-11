


## How to build the cdh container?

```bash
docker build -t docker-cdh54 .
docker run -v /root/docker:/root -p 8042:8042 -p 8088:8088 -p 8020:8020 -p 8888:8888 -p 11000:11000 -p 11443:11443 -p 9090:9090 -d -ti --privileged=true docker-cdh54
docker attach 922ac2f47d93
```
## common command lines

### How to list images with docker

    docker images

### How to list (running) containers

    docker ps

Output example:
```
CONTAINER ID        IMAGE                   COMMAND                  CREATED             STATUS              PORTS                                                                                        NAMES
9265bc46c9ce        rabbitmq:3-management   "docker-entrypoint..."   2 hours ago         Up 2 hours          4369/tcp, 5671/tcp, 0.0.0.0:5672->5672/tcp, 15671/tcp, 25672/tcp, 0.0.0.0:15672->15672/tcp   laughing_lamarr
3c579f0d72f8        mysql:5.7               "docker-entrypoint..."   2 hours ago         Up 2 hours          0.0.0.0:3306->3306/tcp, 33060/tcp          thirsty_fermi
```    

### how to list the ip addresses of all the containers

    docker ps | tail -n +2 | while read cid b; do echo -n "$cid\t"; docker inspect $cid | grep IPAddress | cut -d \" -f 4; done
    
### how to list the ip addresses of one container by name
    
    docker inspect --format '{{ .NetworkSettings.Networks.dockerdevstack_default.IPAddress }}' <name of container>

### How to stop a containers

    docker stop <container id>

for example

    docker stop 9265bc46c9ce

### How to attach to a background container

    docker attach 9265bc46c9ce

From docker 1.3 onwards.

    docker exec -it <containerIdOrName> bash   

### How to exit a container without shutting down the container

You can disconnect and leave the container running by typing Ctrl+p followed by Ctrl+q.

## How to use the Cloudera Hadoop Docker image

https://www.cloudera.com/documentation/enterprise/5-6-x/topics/quickstart_docker_container.html



```bash
docker run -v /home/mathieu/datashare/dev:/home/mathieu/datashare/dev -p 8042:8042 -p 8088:8088 -p 8020:8020 -p 8888:8888 -p 11000:11000 --hostname=quickstart.cloudera --privileged=true -t -i cloudera/quickstart /usr/bin/docker-quickstart
```
or my own baseline
```bash
docker run -v /home/mathieu/datashare/dev:/home/mathieu/datashare/dev -p 8042:8042 -p 8088:8088 -p 8020:8020 -p 8888:8888 -p 11000:11000 --hostname=quickstart.cloudera --privileged=true -t -i cloudera/base2017-10-20 /usr/bin/docker-quickstart
```

# How to start an image

    docker run -ti YOUR_IMAGE /bin/sh

    docker run -ti --entrypoint /bin/sh YOUR_IMAGE

# Useful Docker images to start quickly for development

## gcc

    docker run  -it gcc:8.2 sh

run mount local volume

    docker run -it -v $(pwd):/app  gcc:8.2 sh


## ubuntu

    docker run -t -i -v <host_dir>:<container_dir>  ubuntu /bin/bash

## run retrace base image

    docker run -it -v $(pwd):/app retrace-base-image:0.2 bash

## How to start a Memcached container for local dev on the host

    docker run --name some-memcached -p 11211:11211 -d  memcached

# Docker Compose

## Compose up

    docker-compose up -d
    
### With override files
    
    docker-compose -f docker-compose.yml -f docker-compose.override.yml up -d
    
## tear down the whole stack

    docker-compose down
    
## tear down service

    docker-compose stop <name of my service>

## tail logs

    docker-compose logs -f <name of service>
    
## combine docker-compose up with logs

    docker-compose up -d && docker-compose logs -f

## open terminal on container via docker-compose

    docker-compose exec <name of service> bash



# Google Cloud authentication


## Login

Authenticate docker with gcr by downloading for your platform docker-credential-gcr and add it to your path: https://github.com/GoogleCloudPlatform/docker-credential-gcr/releases

Run the docker-credential-gcr tool to configure and authenticate docker with gcloud.

    docker-credential-gcr configure-docker
    docker-credential-gcr gcr-login    

## Logout

    docker-credential-gcr gcr-logout
    
--> ~/.config/gcloud/docker_credentials.json is deleted



