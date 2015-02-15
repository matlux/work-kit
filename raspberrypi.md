# Special Rasberrypi


# On Mac


## how to write an image onto an sd card

    sudo dd if=~/Downloads/my_pi.img of=/dev/disk4 bs=1m

change disk4 to whatever number your sd device in bound to

## how to backup an image from an sd card

    sudo dd of=~/Downloads/my_pi.img if=/dev/disk4 bs=1m

## how to only backup 8GB from a 32GB

    sudo dd of=~/Downloads/matpi_2015-01-13.img if=/dev/disk5 bs=1m count=7948

## how to change hostname 

    sudo vim /etc/hostname 
    sudo /etc/init.d/hostname.sh
    sudo vim /etc/hosts

## how to change ssh fingerprint on a server

* Step # 1: Delete old ssh host keys

Login as the root and type the following command to delete files on your SSHD server:

    # /bin/rm -v /etc/ssh/ssh_host_*

* Step # 2: Reconfigure OpenSSH Server

Now create a new set of keys on your SSHD server, enter:

    # dpkg-reconfigure openssh-server

## How to setup Clojure dev environment on Raspberry pi

setup the repl timout to a very large value inside the project.clj:

```
:repl-options {:init-ns user
	       :timeout 300000}
```

start repl headless with the ip address of the pi:
```
lein repl :headless :host 192.168.***.*** :port 4567
```

on mac connect onto:
```
nfs://matpi.local/home/mathieu/clojure
lein repl :connect nrepl://192.168.*.*:4567
```

# Printers

## How to setup an HP printer like the Office Deskjet 8600 ?

* sudo apt-get install hplib hplib-gui
* sudo groupadd printadmin
* sudo usermod -aG printadmin pi
* add `SystemGroup printadmin` at the end of /etc/cups/cupsd.conf
* reboot machine
* http://localhost:631 or go to `Menu -> Preferences -> HPLIB Toolbox`
* Follow install process to add printer

[see this](https://bbs.archlinux.org/viewtopic.php?id=35567) for more info
