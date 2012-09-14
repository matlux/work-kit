# vi vim tips


## how to display/remove line number 
    :set number

    :set number&


## set use of mouse

    :set mouse=a

## split screen and open extra file side by side


    :vsplit [filename]


## Diff

    Vi –d file1 file2

## Column resize
    10 ctrl-w < or >

## Xml indentation
    : %!xmllint --format -

## Substitutions

    :%s/foo/bar/g
        Find each occurrence of 'foo', and replace it with 'bar'.

    :%s/foo/bar/gc
        Change each 'foo' to 'bar', but ask for confirmation first.

    :%s/\<foo\>/bar/gc
        Change only whole words exactly matching 'foo' to 'bar'; ask for confirmation.

    :%s/foo/bar/gci
        Change each 'foo' (case insensitive) to 'bar'; ask for confirmation.
        This may be wanted after using :set noignorecase to make searches case sensitive (the default).

    :%s/foo/bar/gcI
        Change each 'foo' (case sensitive) to 'bar'; ask for confirmation.
        This may be wanted after using :set ignorecase to make searches case insensitive.

    http://vim.wikia.com/wiki/Search_and_replace

## install and use sshpass

    tar xvfz /cygdrive/c/Users/UK879907/Downloads/sshpass-1.04.tar.gz
    cd sshpass-1.04/
    ./configure

## open multiple files at once with tabulations
    
    vim -p file1 file2 ...

## how to change tabulation

    g t
