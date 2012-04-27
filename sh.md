

## find files not recognised by system. 

Using pipe within find command

    find ~/Downloads/*.* -type f -exec sh -c 'file "$1" |grep ": data"' pipo {} \;
    
## find none valid jpeg files. 

Using a pipe within a find command

    find *.JPG -type f -exec sh -c 'file "$1" | grep -v JPEG' pipo {} \;
    find *.jpg -type f -exec sh -c 'file "$1" | grep -v JPEG' pipo {} \;

## How to count the number of non-empty (lines not containing spaces only) non-commented lines (starting with hash sign #) on scripts and ruby

    grep -v '^[[:space:]]*#' fileName.rb | grep -v '^[[:space:]]*$' | wc -l


## How to repeat a command & how to display process tree in real time for a given user on Linux a la 'ProcessExplorer'

    while x=0; do clear; ps faux [pid] | grep svcjvmrx; sleep 2; done

## How to display the env variable of a process

    cat /proc/[pid]/environ | tr '\0' '\n'

## How to display a break down of meaningfull (greater than 100M) disk space utilisation for a directory

    du -h ./ | grep -E "(([[:digit:]]{3}M{1})|(G{1}))[[:space:]]"


## How to delete a line of shell history

     history -d [line_number]
