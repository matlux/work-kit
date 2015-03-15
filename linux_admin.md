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

### on Mac

    sudo route -n add 10.8.0.0/24 gw 192.168.77.17

## Delete network route on linux

    sudo route delete -net 10.8.0.0 netmask 255.255.255.0 gw 192.168.77.17

### on Mac

    sudo route -n delete 10.8.0.0/24 gw 192.168.77.17

## How to trace all systems call and file access on linux?

    strace -f -o log [process2run] [args]

# SSH stuff

## ssh tuneling

    ssh -L22222:localhost:222 remoteuser@remotehost

## How to change ssh key passphrase

    ssh-keygen -p -f ~/.ssh/id_rsa

## How to change ssh fingerprint on a server

* Step # 1: Delete old ssh host keys

Login as the root and type the following command to delete files on your SSHD server:

    # /bin/rm -v /etc/ssh/ssh_host_*

* Step # 2: Reconfigure OpenSSH Server

Now create a new set of keys on your SSHD server, enter:

    # dpkg-reconfigure openssh-server

Sample output:

```
Creating SSH2 RSA key; this may take some time ...
Creating SSH2 DSA key; this may take some time ...
Restarting OpenBSD Secure Shell server: sshd.
```

## How to Generate an RSA key to connect onto another computer?

```
mkdir ~/.ssh
chmod 700 ~/.ssh
ssh-keygen -t rsa
```

You'll get something like this:
```
Generating public/private rsa key pair.
Enter file in which to save the key (/home/b/.ssh/id_rsa):
Enter passphrase (empty for no passphrase):
Enter same passphrase again:
Your identification has been saved in /home/b/.ssh/id_rsa.
Your public key has been saved in /home/b/.ssh/id_rsa.pub.
```

Then append the content of `id_rsa.pub` into the `~/.ssh/authorized_keys`

Copy and paste or the following will work.
```
cp authorized_keys authorized_keys_Backup
cat id_rsa.pub >> authorized_keys
```

Alternativelly you can use this handy command:
```
ssh-copy-id <username>@<host>
```
## How to Disable Password Authentication for SSH

```
vim /etc/ssh/sshd_config
```

modify the following:
```
ChallengeResponseAuthentication no
PasswordAuthentication no
UsePAM no
PubkeyAuthentication yes
AuthorizedKeysFile .ssh/authorized_keys
```
`PasswordAuthentication no` is the most important.

Once this is done, restart the SSH daemon to apply the settings:
```
/etc/init.d/sshd restart
```
see [this link](http://support.hostgator.com/articles/specialized-help/technical/how-to-disable-password-authentication-for-ssh) for more reference.

On Synology you can restart the daemon with:
Save the file and restart the SSH daemon. The easier is to use the GUI/WEB login. Click on the Control Panel -> Terminal. Uncheck Enable SSH Service, apply, check it again, and press apply again.

For Synology NAS follow [this](http://www.eldemonionegro.com/blog/archivos/2012/08/19/how-to-securely-activate-ssh-into-your-synology-diskstation-with-ssh-keys-and-no-root-login) for more details.

## screen

## disown

## find a port open by a process

    lsof -i tcp:8000

    netstat -tulpn | grep 8000

## How to change keyboard layout from command line

    setxkbmap gb

## add a user with useradd

    sudo useradd --uid 3395 --gid users -d /home/joeblog joeblog

## add a user with adduser (higher level command interactive)

    adduser joeblog

# zipping

## How to 7zip a bunch of files

    7za a -t7z archive.7z file1 files*

## How to test 7zip files

    7z t archive.7z file1 files* -r

see [this](http://www.dotnetperls.com/7-zip-examples) for more details

# Partimage - How to use it to backup and restor disk images

## How to Save image

* mkdir /mnt/samba
* mount -t cifs //hal/public /mnt/samba -o uid=1000,iocharset=utf8,username=[username],password=[password],sec=ntlm
* partimage save /dev/sda1 /mnt/samba/staff/[username]/D600_training_img.pimg.gz
* dd if=/dev/hda of=/mnt/samba/staff/[username]/sda.mbr count=1 bs=512
* sfdisk -d /dev/hda > /mnt/samba/staff/[username]/sda.pt

## How To Restore:
* dd if=/mnt/samba/staff/[username]/sda.mbr of=/dev/sda
* sfdisk /dev/hda < /mnt/samba/staff/[username]/sda.pt
* mkdir /mnt/samba
* mount -t cifs //hal/public /mnt/samba -o iocharset=utf8,username=[username],sec=ntlm
* partimage -e restore /dev/sda1 /mnt/samba/staff/[username]/D600_training_img.pimg.gz.000
