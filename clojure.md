
## Display Classpath

    (println (seq (.getURLs (java.lang.ClassLoader/getSystemClassLoader))))

## Install lein behind a proxy

    export https_proxy='https://user:pass@proxy:8080'
    export http_proxy='http://user:pass@proxy:8080'
    wget --proxy-user=user --proxy-password='pass' --no-check-certificate https://raw.github.com/technomancy/leiningen/preview/bin/lein

## How to start emacs from commandline on mac

```sh
$ type emacs
emacs is /opt/Utils/linux/bin/emacs
$ cat /opt/Utils/linux/bin/emacs
#!/bin/sh
/Applications/Emacs.app/Contents/MacOS/Emacs "$@"
```
