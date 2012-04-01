
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
    
 special for Git:  export https_proxy='https://user:pass@proxy:8080' 
 Special for wget: 
    export http_proxy=proxy:8080 
    wget --proxy-user=user --proxy-password='pass' http:/url2get/example-1.0.tar.gz 
 
 Special ruby gem:  export http_proxy='http://user:pass@proxy:8080' 
 
 Special Yum, edit yum.conf and add:
    
    proxy=http://proxy:8080 
    proxy_username=user 
    proxy_password=pass

## How to count the number of non-empty (lines not containing spaces only) non-commented lines (starting with hash sign #) on scripts and ruby

    grep -v '^[[:space:]]*#' fileName.rb | grep -v '^[[:space:]]*$' | wc -l
