# SVN tips

## How to checkout

    svn checkout file:///var/svn/repos/test mine
    A    mine/a
    A    mine/b
    A    mine/c
    A    mine/d
    Checked out revision 20.

## How to create a branch

    svn copy -r1234 http://hostname:8080/myproject/trunk http://hostname:8080/myproject/branches/pilot -m "my message"


## How to switch local repo to pilot branch

    svn switch http://hostname:8080/myproject/branches/pilot
    
## How to merge with SVN ?

We want to merge rev 6840 from pilot onto trunk.


Switch to trunk

    svn switch http://hostname:8080/myproject/trunk

Check you're on trunk  (optional)

    svn info

Check the revision (e.g. 1842) you're interested in  (optional)

    svn log -r 1842 http://hostname:8080/myproject/branches/pilot

Make sure your current directory is on the root of the local repository i.e. at the level of the root folder in the trunk (the trunk/. folder)

~/myproject  (in this example)

Make sure your local working directory is "clean"

    svn status

(should return only references to external links, beginning with "X")
if not apply 5

Revert all your local changes (it will delete all your local changes that were not committed previously)

    svn revert -R .


Merge

    svn merge -c 1842 --ignore-ancestry http://hostname:8080/myproject/branches/pilot

If there are no conflict you can Commit *

    svn ci -m "MERGE pilot [1842] - test commit for merge from SVN"
    
## How to set a file as executable

    svn propset svn:executable ON deploy.sh

## How to revert all files

    svn st | grep ^M | awk '{print $2}' | xargs svn revert

## How to list all users of the repo

    svn log -q | grep -e '^r' | awk 'BEGIN { FS = "|" } ; { print $2 }' | sort | uniq


## How to list all svn:externals recursively

    svn propget svn:externals -R
