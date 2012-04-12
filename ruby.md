


## Install Ruby Gem from source

    https://raw.github.com/albertsj1/Misc-Scripts/master/install_gems.sh

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
