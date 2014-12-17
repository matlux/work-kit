# Special Rasberrypi


# On Mac


## how to write an image onto an sd card

    sudo dd if=~/Downloads/my_pi.img of=/dev/disk4 bs=1m

change disk4 to whatever number your sd device in bound to

## how to backup an image from an sd card

    sudo dd of=~/Downloads/my_pi.img if=/dev/disk4 bs=1m
