

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

## show PATH directory access rights, show they are accessible. useful for git

    echo $PATH |tr ':' '\n' |xargs ls -ld

## dos to unix:

    sed 's/\r$//' dos.txt > unix.txt

## unix to dos:

    sed 's/$/\r/' unix.txt > dos.txt

## monitor example

    #!/bin/sh
    while x=0; do
      clear;  find . -name "*.log" | xargs ls -al;
      md5sum ~/work/target/grid-webapp.war
      md5sum ~/deploy/webapps/data-grid-webapp.war
      echo grid log
      tail --bytes=5120 ~/logs/grid.log
      sleep 2
    done

## remove empty strings

    cat afile.txt | grep -v "^$"

## for example

for i in {20..39}; do host=machine$i; echo $host; ssh $host ruby -v ; done
