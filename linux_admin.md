## mount samba share from linux

 Prerequisite:

    yum install cifs-utils

remote Windows machine windowspc
share name on windowspc:data
username:  tomsmith
password:blogs

password: Create a local mount point. For example:

mkdir /mnt/win

Edit the /etc/fstab file and add a line like:


//windowspc/data /mnt/win cifs user,uid=500,rw,suid,username=tomsmith,password=blogs 0 0


## use of a proxy with various Unix applications or cygwin  
    
 special for Git:

    export https_proxy='https://user:pass@proxy:8080' 

 Special for wget: 

    export http_proxy=proxy:8080 
    wget --proxy-user=user --proxy-password='pass' http:/url2get/example-1.0.tar.gz 
 
 Special ruby gem:

    export http_proxy='http://user:pass@proxy:8080' 
 
 Special Yum, edit yum.conf and add:
    
    proxy=http://proxy:8080 
    proxy_username=user 
    proxy_password=pass

 Special leinengen
 
    export HTTP_PROXY=http://user:pass@proxy:8080
    export HTTPS_PROXY=https://user:pass@proxy:8080
    

## Add network route on linux

    sudo route add -net 10.8.0.0 netmask 255.255.255.0 gw 192.168.77.17

## How to trace all systems call and file access on linux?

    strace -f -o log [process2run] [args]

## ssh tuneling

    ssh -L22222:localhost:222 remoteuser@remotehost

## How to change ssh key passphrase

    ssh-keygen -p -f ~/.ssh/id_rsa

screen

disown

## find a port open by a process

    lsof -i tcp:8000

    netstat -tulpn | grep 8000

## How to change keyboard layout from command line

    setxkbmap gb

## add a user with useradd

    sudo useradd --uid 3395 --gid users -d /home/joeblog joeblog

## add a user with adduser (higher level command interactive)

    adduser joeblog
