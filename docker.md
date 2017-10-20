

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
    
### How to attach to a container

    docker attach 922ac2f47d93

### How to exit a container without shutting down the container

You can disconnect and leave the container running by typing Ctrl+p followed by Ctrl+q.

## How to use the Cloudera Hadoop Docker image

https://www.cloudera.com/documentation/enterprise/5-6-x/topics/quickstart_docker_container.html



```bash
docker run -v /home/mathieu/datashare/dev:/home/mathieu/datashare/dev -p 8042:8042 -p 8088:8088 -p 8020:8020 -p 8888:8888 -p 11000:11000 --hostname=quickstart.cloudera --privileged=true -t -i cloudera/quickstart /usr/bin/docker-quickstart
```

