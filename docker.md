

## How to install and use boot2docker on Mac

see http://penandpants.com/2014/03/09/docker-via-homebrew/

```bash
brew cask install virtualbox
brew install docker
brew install boot2docker
boot2docker init
boot2docker -v up

export DOCKER_HOST=tcp://192.168.59.103:2376
export DOCKER_CERT_PATH=/Users/mathieu/.boot2docker/certs/boot2docker-vm
export DOCKER_TLS_VERIFY=1

boot2docker ssh
sudo curl -o /var/lib/boot2docker/profile https://gist.githubusercontent.com/garthk/d5a17007c277aa5c76de/raw/3d09c77aae38b4f2809d504784965f5a16f2de4c/profile
exit
boot2docker down
boot2docker -v up
```

See http://stackoverflow.com/questions/30980474/docker-complains-about-invalid-certificate-after-update-to-v1-7-0 for hack

```bash
wait4eth1() {
        CNT=0
        until ip a show eth1 | grep -q UP
        do
                [ $((CNT++)) -gt 60 ] && break || sleep 1
        done
        sleep 1
}
wait4eth1
```

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
# Useful Docker images to start quickly for development

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


## Purging All Unused or Dangling Images, Containers, Volumes, and Networks

ref: https://www.digitalocean.com/community/tutorials/how-to-remove-docker-images-containers-and-volumes#:~:text=Remove%20all%20images,docker%20images%20%2Da

Docker provides a single command that will clean up any resources — images, containers, volumes, and networks — that are dangling (not associated with a container):

    docker system prune
 
To additionally remove any stopped containers and all unused images (not just dangling images), add the -a flag to the command:

    docker system prune -a
    



