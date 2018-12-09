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

    sudo route -n add 10.8.0.0/24 192.168.77.17

## Delete network route on linux

    sudo route delete -net 10.8.0.0 netmask 255.255.255.0 gw 192.168.77.17

### on Mac

    sudo route -n delete 10.8.0.0/24 192.168.77.17

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

# Misc low level disk manipulations

## How to setup swap on encrypted ext4 luks file system as a file

```
sudo fallocate -l 32G /swapfile 
sudo mkswap /swapfile 
sudo swapon /swapfile
```

ref: https://askubuntu.com/questions/248158/how-do-i-setup-an-encrypted-swap-file

## How to re-install grub

```
cryptsetup open /dev/nvme0n1p7 cryptoroot  # only for luks drive
mkdir /mnt/cryptoroot
mount /dev/mapper/cryptoroot /mnt/cryptoroot
mount --bind /dev/ /mnt/cryptoroot/dev && mount --bind /dev/pts /mnt/cryptoroot/dev/pts && mount --bind /proc /mnt/cryptoroot/proc && mount --bind /sys /mnt/cryptoroot/sys
mount /dev/nvme0n1p3 /mnt/cryptoroot/boot
mount /dev/nvme0n1p1 /mnt/cryptoroot/boot/efi
chroot /mnt/cryptoroot
apt purge --auto-remove grub-pc
#Hit Enter to confirm the removal.

apt install grub-pc

grub-install /dev/nvme0n1
update-grub 
update-initramfs -k all -c
```

/etc/crypttab

    cat /etc/crypttab
    
```
cat /etc/crypttab
#cryptswap1 UUID=6e21de8a-d5b5-42d5-aadf-34cbc78c4d74 /dev/urandom swap,offset=1024,cipher=aes-xts-plain64
cryptoroot UUID=69ffeee2-ce39-4acc-9389-6c8638e3b048 none luks
```

/etc/fstab

    cat /etc/fstab

```
# /etc/fstab: static file system information.
#
# Use 'blkid' to print the universally unique identifier for a
# device; this may be used with UUID= as a more robust way to name devices
# that works even if disks are added and removed. See fstab(5).
#
# <file system> <mount point>   <type>  <options>       <dump>  <pass>
# / was on /dev/nvme0n1p7 during installation
/dev/mapper/cryptoroot /               ext4    errors=remount-ro 0       1

# /boot/efi was on /dev/nvme0n1p1 during installation
UUID=7420-AA7C  /boot/efi       vfat    umask=0077      0       1
#/dev/mapper/cryptswap1 none swap sw 0 0
/swapfile none swap sw 0 0

```

If above does not work try this to force the efi re-install
```
apt-get install --reinstall grub-efi-amd64
```


That's an alternative but not as good.
```
sudo mount /dev/sdXXX /mnt
sudo mount /dev/sdXY /mnt/boot
sudo mount /dev/sdXX /mnt/boot/efi
for i in /dev /dev/pts /proc /sys /run; do sudo mount -B $i /mnt$i; done
sudo chroot /mnt
grub-install /dev/sdX
update-grub 

#Note : sdX = disk | sdXX = efi partition | sdXY = boot partition | sdXXX = system partition 
```

## How to find information about disk partitions

```
lsblk
sfdisk -d /dev/nvme0n1
gparted
ls -l /dev/disk/by-uuid
```


## copy useful examples

### copy preserving all attributes and links (useful for root copy)

    sudo rsync -axHAWXS --numeric-ids --info=progress2 /mnt/olddisk/ /mnt/newdisk/
    
ref: https://superuser.com/questions/307541/copy-entire-file-system-hierarchy-from-one-drive-to-another


## Mount useful examples

### readonly

    sudo mount -o ro /dev/sda1 /media/2tb
    


## How to ext4 + LUKS

```
sudo apt-get install luksipc
sudo luksipc -d /dev/nvme0n1p7
cryptsetup luksOpen --key-file /root/initial_keyfile.bin /dev/nvme0n1p7 cryptoroot
mkfs -t ext4 /dev/mapper/cryptroot
```



### open luks partition

    cryptsetup luksOpen --key-file /root/initial_keyfile.bin /dev/nvme0n1p7 cryptoroot

### close LUKS partition

    cryptsetup close cryptoroot


### list keys

    cryptsetup luksDump /dev/<device> | grep BLED
    
### add key

    cryptsetup luksAddKey /dev/<device> (/path/to/<additionalkeyfile>)

## Partimage - How to use it to backup and restor disk images

### How to Save image

* mkdir /mnt/samba
* mount -t cifs //hal/public /mnt/samba -o uid=1000,iocharset=utf8,username=[username],password=[password],sec=ntlm
* partimage save /dev/sda1 /mnt/samba/staff/[username]/D600_training_img.pimg.gz
* dd if=/dev/hda of=/mnt/samba/staff/[username]/sda.mbr count=1 bs=512
* sfdisk -d /dev/hda > /mnt/samba/staff/[username]/sda.pt

### How To Restore:
* dd if=/mnt/samba/staff/[username]/sda.mbr of=/dev/sda
* sfdisk /dev/hda < /mnt/samba/staff/[username]/sda.pt
* mkdir /mnt/samba
* mount -t cifs //hal/public /mnt/samba -o iocharset=utf8,username=[username],sec=ntlm
* partimage -e restore /dev/sda1 /mnt/samba/staff/[username]/D600_training_img.pimg.gz.000

# How to use CloneZilla and partclone

## saving image with CloneZilla

```
ocs-sr -q2 -c -j2 -z1p -i 4096 -sfsck -senc -p choose savedisk 2018-11-04-16-img nvme0n1
```

## restore image

```
cat 2018-11-04-16-img/nvme0n1p7.ext4-ptcl-img.gz.* | gzip -d -c | sudo partclone.ext4 -r -s - -W -O images/restored.img
mount images/restored /mnt/olddisk
sudo rsync -axHAWXS --numeric-ids --info=progress2 /mnt/olddisk/ /mnt/newdisk/
```


# How to keep using Dropbox even if you don’t use unencrypted ext4 (workaround)

https://metabubble.net/linux/how-to-keep-using-dropbox-even-if-you-dont-use-unencrypted-ext4-workaround/

```
mv Dropbox Dropbox.bak
mkdir Dropbox
dd if=/dev/zero of=~/.dropbox/storage bs=1M count=3072
mkfs.ext4 /home/<username>/.dropbox/storage
chattr +i /home/<user>/Dropbox
mkfs.ext4 /home/<username>/.dropbox/storage
```

in /etc/fstab:
```
/home/<username>/.dropbox/storage /home/<username>/Dropbox ext4 defaults,user_xattr,loop 0 0
```

```
cp -a Dropbox.bak/* Dropbox/
```

# How to use Sparse Files

https://wiki.archlinux.org/index.php/sparse_file


# install new images

## router debian

```sh
sudo apt-get install vim htop iftop isc-dhcp-server bind9 git
```
