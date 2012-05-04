

## How to set a file as executable

    svn propset svn:executable ON deploy-application.sh

## How to revert all files

    svn st | grep ^M | awk '{print $2}' | xargs svn revert
