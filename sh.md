

## find files not recognised by system. 

Using pipe within find command

    find ~/Downloads/*.* -type f -exec sh -c 'file "$1" |grep ": data"' pipo {} \;
    
## find none valid jpeg files. 

Using pipe within find command

    find *.JPG -type f -exec sh -c 'file "$1" | grep -v JPEG' pipo {} \;
    find *.jpg -type f -exec sh -c 'file "$1" | grep -v JPEG' pipo {} \;