
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


## ZeroMq examples

https://github.com/matlux/cljzmq-examples/blob/master/test/cljzmq_examples/serialization/clojure_test.clj


## Clojure distinctive

short feedback loop
repl
code exploration
homoiconic
functional
extensibility
structurual deiting
power of expression
Data oriented

## history
1936 lambda calculus (alonzo church)
1958 lisp (john McCarthy)
     History of Lisp, John McCarthy 1979
2000 Ideal Hash trees (Phil Bagwell)
2006 Out of the tar pit  (Ben Moseley and Peter Marks)
2007 Clojure (Rich Hickey)
2021 Data-Oriented programming

## Principal of Data-Oriented programming

1 Separate code from data
2 Represent data wtih generic data structures
3 immutable data
