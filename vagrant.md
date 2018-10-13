

## How to install Vagrant on Ubuntu

```
sudo apt-get update
sudo apt install vagrant
```
Ref: https://linuxize.com/post/how-to-install-vagrant-on-ubuntu-18-04/

## Get started with Vagrant


```
mkdir standard-ubuntu1604
cd standard-ubuntu1604/
vagrant init ubuntu/xenial64
vim Vagrantfile
```


```
Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/xenial64"
end
```

### Start image

    vagrant up

### Stop image

    vagrant halt

### delete image
    vagrant destroy

### ssh into the virtual machine
    vagrant ssh


## How to mount a share

!(share a folder on vbox)[https://i.stack.imgur.com/Gu70v.png]

    sudo mkdir /datashare
    sudo chown vagrant:vagrant /datashare
    sudo mount -t vboxsf -o uid=$UID,gid=$(id -g) datashare /datashare
