
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