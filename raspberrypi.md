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

