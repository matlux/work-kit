

## find files not recognised by system. 

Using pipe within find command

    find ~/Downloads/*.* -type f -exec sh -c 'file "$1" |grep ": data"' pipo {} \;
    
## find none valid jpeg files. 

Using a pipe within a find command

    find *.JPG -type f -exec sh -c 'file "$1" | grep -v JPEG' pipo {} \;
    find *.jpg -type f -exec sh -c 'file "$1" | grep -v JPEG' pipo {} \;

## How to find multiple files at the same time

    find . \( -name \*foo\* -o -name \*bar\* \)

## How to count the number of non-empty (lines not containing spaces only) non-commented lines (starting with hash sign #) on scripts and ruby

    grep -v '^[[:space:]]*#' fileName.rb | grep -v '^[[:space:]]*$' | wc -l


## How to repeat a command & how to display process tree in real time for a given user on Linux a la 'ProcessExplorer'

    while x=0; do clear; ps faux [pid] | grep svcjvmrx; sleep 2; done

## How to display the env variable of a process

    cat /proc/[pid]/environ | tr '\0' '\n'

## How to display a break down of meaningfull (greater than 100M) disk space utilisation for a directory

    du -h ./ | grep -E "(([[:digit:]]{3}M{1})|(G{1}))[[:space:]]"

## How to do a recursive grep with color

    grep -rni --color=always error /tmp/data/logs/*


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

## Display 2 arguments from ps with sed: (find pid and member name of a coherence cluster)

    ps faux | grep -E '(user1|user2)' |  grep -v grep | grep 'tangosol' |  sed 's#[^ ]*[ ]*\([^ ]*\).*member=\([^ ]*\).*#\1 \2#'

## awk example: find pid and member name of a coherence cluster

file name: test.awk

    {
        printf($2 " ");
        for(i=1; i<NF; ++i) {
                if($i ~ /.*member=.*/) {
                        split($i, tab, "=");
                        print tab[2];
                }
        }
    }

call:

    while x=0; do clear;  ps faux | grep -E '(user1|user2)' | grep -v grep | grep 'tangosol' | awk -f test.awk; sleep 2; done

or simplified

    ps faux | grep -E '(user1|user2)' | grep -v grep | grep 'tangosol' | awk -f test.awk 

## How to see processes belonging to a couple of users

    while x=0; do clear; ps faux | grep -E '(user1|user2)'; sleep 2; done

## How to find a jar file containing a specific file

    find . -name "*.jar" | xargs grep -n "engine.properties"

## How to sort folders by size

    du -h | sort -n
    ls -lSh


## How to find files created on a specific date

Example: To find all files modified on the 7th of June, 2006:

    find . -type f -newermt 2007-06-07 ! -newermt 2007-06-08

To find all files accessed on the 29th of september, 2008:

    find . -type f -newerat 2008-09-29 ! -newerat 2008-09-30

Or, files which had their permission changed on the same day:

    find . -type f -newerct 2008-09-29 ! -newerct 2008-09-30

If you don't change permissions on the file, 'c' would normally correspond to the creation date, thoug
