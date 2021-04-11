

##  install Sublime text

```
brew cask install sublime-text
```

## install java



https://medium.com/@chamikakasun/how-to-manage-multiple-java-version-in-macos-e5421345f6d0

```


brew cask install homebrew/cask-versions/adoptopenjdk8
brew cask install AdoptOpenJDK/openjdk/adoptopenjdk11

/usr/libexec/java_home -V

jenv add /Library/Java/JavaVirtualMachines/adoptopenjdk-11.jdk/Contents/Home
jenv versions
jenv add /Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home
jenv global 11
```
or
```
brew tap adoptopenjdk/openjdk

brew cask install adoptopenjdk8
brew cask install adoptopenjdk9
brew cask install adoptopenjdk10
brew cask install adoptopenjdk11


```

## install lein 


```
brew update
brew install leiningen
```

## install Slack

```
brew cask install slack
```

## install VS Studio

```
brew install --cask visual-studio-code
```

## install Seashore

```
brew cask install seashore
```

## Vagrant

https://sourabhbajaj.com/mac-setup/Vagrant/README.html


    brew cask install virtualbox

Now install Vagrant either from the website or use homebrew for installing it.

    brew cask install vagrant

Vagrant-Manager helps you manage all your virtual machines in one place directly from the menubar.

    brew cask install vagrant-manager


## Docker

https://medium.com/crowdbotics/a-complete-one-by-one-guide-to-install-docker-on-your-mac-os-using-homebrew-e818eb4cfc3


## install clojure

```
brew install clojure/tools/clojure
```