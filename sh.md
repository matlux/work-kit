

## find files not recognised by system. 

Using pipe within find command

    find ~/Downloads/*.* -type f -exec sh -c 'file "$1" |grep ": data"' pipo {} \;
    
## find none valid jpeg files. 

Using pipe within find command

    find *.JPG -type f -exec sh -c 'file "$1" | grep -v JPEG' pipo {} \;
    find *.jpg -type f -exec sh -c 'file "$1" | grep -v JPEG' pipo {} \;

## How to count the number of non-empty (lines not containing spaces only) non-commented lines (starting with hash sign #) on scripts and ruby

    grep -v '^[[:space:]]*#' fileName.rb | grep -v '^[[:space:]]*$' | wc -l
