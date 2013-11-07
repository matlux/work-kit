

## How to set a file as executable

    svn propset svn:executable ON deploy-application.sh

## How to revert all files

    svn st | grep ^M | awk '{print $2}' | xargs svn revert

## How to list all users of the repo

    svn log -q | grep -e '^r' | awk 'BEGIN { FS = "|" } ; { print $2 }' | sort | uniq


## How to list all svn:externals recursively

    svn propget svn:externals -R
