


## Install Ruby Gem from source

    https://raw.github.com/albertsj1/Misc-Scripts/master/install_gems.sh

## Install Ruby Gem from source into an alternative directory and by non root user, without zlib and yalm installed by root

### main procedure

    wget http://ftp.ruby-lang.org/pub/ruby/1.9/ruby-1.9.3-p125.tar.gz
    tar xvfz ruby-1.9.3-p125.tar.gz
    cd ruby-1.9.3-p125
    export LDFLAGS="-L/home/user/lib/yaml/lib"
    export CPPFLAGS="-I/home/user/lib/yaml/include"
    ./configure -prefix=/home/user/local/ruby  
    CPUS="$(grep processor /proc/cpuinfo | wc -l)"
    make -j $CPUS
    make install
    export PATH=/home/user/local/ruby:.:$PATH



See:
    http://www.r-chart.com/2010/06/installing-ruby-on-linux-as-user-other.html

### Dependencies Install

This is to do before the compilation of Ruby if the dependencies are missing!!
We are in a dear situation as we have no root access and some of the dependencies are missing. We are getting desperate but we can do it this way:

Compile:

Zlib



Yaml

    tar xvfz yaml-0.1.4.tar.gz
    cd yaml-0.1.4/
    ./configure --prefix= /apps/dev_platform/lib/yaml
    make
    make install

Openssl

    export LDFLAGS="-L/apps/dev_platform/lib/yaml/lib -L/apps/dev_platform/zlib/lib -L/apps/dev_platform/openssl-1.0.1a/lib"
    export CPPFLAGS="-I/apps/dev_platform/lib/yaml/include -I/apps/dev_platform/zlib/include -I/apps/dev_platform/openssl-1.0.1a/include"
    tar xvfz openssl-1.0.1a.tar.gz
    ./config --prefix=/apps/dev_platform/openssl-1.0.1a --shared
    make
    make test
    make install

readline
    tar xvfz 
    ./configure --prefix=/apps/dev_platform/lib/readline-6.1


## Install Ruby gem on windows with Cygwin 

    tar xvfz rubygems-1.8.19.tgz 
    cd rubygems-1.8.19/ 
    ruby setup.rb install 

    http://stevenharman.net/blog/archive/2008/11/12/installing-rubygems-in-cygwin.aspx 

## Install Chef Solo


    gem install chef --no-ri --no-rdoc 

### tutorials


    http://jonathanotto.com/blog/chef-tutorial-in-minutes.html 


    http://www.opinionatedprogrammer.com/2011/06/chef-solo-tutorial-managing-a-single-server-with-chef/ 

    http://illuminatedcomputing.com/posts/2012/02/simple-chef-solo-tutorial/ 



## Install Pocketknife


    gem install pocketknife 

    http://rubygems.org/gems/pocketknife 

## Ruby Tips

### How to display load path

    ruby -e 'puts $:'
    
### Modify $LOAD_PATH

    # Remove the current directory from the load path
    $:.pop if $:.last == '.'
    # Add the installation directory for the current program to
    # the beginning of the load path
    $LOAD_PATH.unshift File.expand_path($PROGRAM_NAME)
    # Add the value of an environment variable

### Modify $LOAD_PATH a la Pocketknife to Use local files if executed from a source code checkout directory, useful for development. 

    lib = Pathname.new($0).expand_path.dirname + '..' + 'lib' + 'yourprogram.rb'
    if lib.exist?
    require 'rubygems'
    $LOAD_PATH.unshift(lib.dirname)
    end
    # here call your entry point/class

    require 'yourprogram'
