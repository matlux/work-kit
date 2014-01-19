

```sh
lsb_release -a
sh -c 'echo "deb http://download.virtualbox.org/virtualbox/debian wheezy contrib" >> /etc/apt/sources.list'
mkdir install
cd install/
mkdir virtualBox
cd virtualBox/
wget -q http://download.virtualbox.org/virtualbox/debian/oracle_vbox.asc -O- | apt-key add -
aptitude update
apt-get install dkms
sudo apt-get install linux-headers-$(uname -r) build-essential
apt-get install linux-headers-$(uname -r) build-essential
apt-get install virtualbox-4.2 --no-install-recommends
wget http://download.virtualbox.org/virtualbox/4.2.6/Oracle_VM_VirtualBox_Extension_Pack-4.2.6-82870.vbox-extpack
VBoxManage extpack install Oracle_VM_VirtualBox_Extension_Pack-4.2.6-82870.vbox-extpack
VBoxManage extpack list
#VBoxManage extpack uninstall 'Oracle VM VirtualBox Extension Pack'
#VBoxManage extpack install Oracle_VM_VirtualBox_Extension_Pack-4.2.6-82870.vbox-extpack
adduser mathieu vboxusers
```
