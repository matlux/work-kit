

## git tips

## create patch of what is in working directory

    git diff > /tmp/mypatch.patch

## apply patch onto working directory

    git apply /tmp/test/wkit2.patch

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
