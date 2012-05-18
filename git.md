

## git tips

## create patch of what is in working directory

    git diff > /tmp/mypatch.patch

## apply patch onto working directory

    git apply /tmp/test/wkit2.patch

## more advanced patch

be on the latest commit
you want to patch from master to HEAD

    git format-patch master
  or
    git format-patch master --stdout > filename.patch

apply patch:

    git apply --check filename.patch
    git apply --stat filename.patch
    git am --signoff < filename.patch

## Useful Aliases

### pimp your history display

    git config --global alias.show-graph "log --graph --pretty=format:'%Cred%h%Creset -%C(yellow)%d%Creset %s %Cgreen(%cr - %an)%Creset' --abbrev-commit --date=relative --all"

### Alias to display aliases

    git config --global alias.alias "config --get-regexp 'alias.*'"

## add an ssh remote

    git remote add origin ssh://user@host:1234/srv/git/example


## Viewing all `git diffs` with vimdiff

    git config --global diff.tool vimdiff
    git config --global difftool.prompt false
    git config --global alias.d difftool

Typing `git d` yields the expected behavior, type `:wq` in vim cycles to the next file in the changeset. 


## good website on GIT

    http://www-cs-students.stanford.edu/~blynn//gitmagic/ch07.html
