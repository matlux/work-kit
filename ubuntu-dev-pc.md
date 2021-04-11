
## Install Dropbox

ref: https://www.linuxbabe.com/ubuntu/install-dropbox-ubuntu-20-04

```
echo "deb [arch=i386,amd64] http://linux.dropbox.com/ubuntu bionic main" | sudo tee -a /etc/apt/sources.list.d/dropbox.list > /dev/null
sudo apt update
sudo apt install python3-gpg dropbox
```

## Install Sublime Text 3

ref: https://linuxize.com/post/how-to-install-sublime-text-3-on-ubuntu-20-04/

```
sudo apt install dirmngr gnupg apt-transport-https ca-certificates software-properties-common curl
curl -fsSL https://download.sublimetext.com/sublimehq-pub.gpg | sudo apt-key add -
sudo add-apt-repository "deb https://download.sublimetext.com/ apt/stable/"
sudo apt install sublime-text
```

## Install Intellij

```
sudo snap install intellij-idea-community --classic
```

## Install vs-code

```
sudo snap install --classic code
```

## Install Skype

```
sudo snap install skype --classic
```
## xournal++

```
sudo snap install xournalpp
```

## Zoom

```
sudo snap install zoom-client
```

## Install grml-rescueboot

```
sudo apt-get install grml-rescueboot
```

Place bootable ISO files in the /boot/grml folder.

```
sudo mv ~/Downloads/<filename.iso> /boot/grml/

Update Grub
```
sudo update-gru

## Install Vagrant and Virtual Box

```
sudo apt install virtualbox

curl -O https://releases.hashicorp.com/vagrant/2.2.10/vagrant_2.2.10_x86_64.deb
sudo apt install ./vagrant_2.2.10_x86_64.deb
```

## Slack

```
sudo snap install slack --classic
```

## install & apt-get etc... script

```
sudo apt install xclip xsel
```

## Install standard commands

```
sudo apt install htop vim
```

## Install docker

```
sudo apt install docker.io
sudo usermod -aG docker mathieu
sudo apt install docker-compose
```

restart machine

### Install Minecraft

```
cd ~/Downloads
wget -o Minecraft.deb https://launcher.mojang.com/download/Minecraft.deb
sudo apt install gdebi-core
sudo gdebi Minecraft.deb
```

#### Install minecraft on Chromebook

ref: https://www.pixelspot.net/2020/01/03/how-to-install-minecraft-java-edition-on-a-chromebook/
crouton only enable:
```
chrome://flags/#crostini-gpu-support
chrome://flags/#exo-pointer-lock
chrome://flags/#enable-pointer-lock-options
## consider
https://optifine.net/home
java -jar OptiFine_1.13.2_HD_U_F5.jar
## install and restart normal launcher
```

### How to install Spotify

```
curl -sS https://download.spotify.com/debian/pubkey_0D811D58.gpg | sudo apt-key add - 
echo "deb http://repository.spotify.com stable non-free" | sudo tee /etc/apt/sources.list.d/spotify.list
sudo apt-get update && sudo apt-get install spotify-client
```

### chrome
ref: https://linuxize.com/post/how-to-install-google-chrome-web-browser-on-ubuntu-20-04/

```
wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
sudo apt install ./google-chrome-stable_current_amd64.deb
```


### .profile

```
export GOPATH=$HOME/go
export PATH="$GOPATH/bin:$PATH"
export EDITOR=$(which subl)
alias cd-guru='cd ~/datashare/dev/resourceguru/guru-website/'
alias cd-guru-docker='cd ~/projects/resource_guru/guru-website/'
alias pbcopy='xclip -selection clipboard'
alias pbpaste='xclip -selection clipboard -o'
export PATH=~/.npm-global/bin:$PATH
```

alternatively

```
export VISUAL=vim
export EDITOR="$VISUAL"
```

### Zerotier

```
curl -s https://install.zerotier.com | sudo bash
sudo zerotier-cli join c7c8172af1f87f4b
```

### Chromebook

ref
https://github.com/dnschneid/crouton
https://www.youtube.com/watch?v=QB9puwi2qTo
https://itsfoss.com/install-linux-chromebook/
https://github.com/zerotier/ZeroTierOne/issues/367
https://www.reddit.com/r/Crouton/comments/5o0oco/lost_password/


```
## download crouton
wget -o crouton https://goo.gl/fd3zc
## ctrl-alt-T  -> Terminal
shell
sudo install -Dt /usr/local/bin -m 755 ~/Downloads/crouton
sudo crouton
sudo crouton -t xfce
sudo chmod 666 /dev/net/tun
lxc config device set penguin tun mode 0666
sudo startxfce4
## ctrl-alt-shift <-
## ctrl-alt-shift ->
```

change password
```
sudo enter-chroot -u root

that will pit you inside the chroot as root. then just

passwd <username>
```

### Install npm

```
sudo apt install npm
npm install -g n
n lts
npm install -g yarn
```

### Aliases

```

```
