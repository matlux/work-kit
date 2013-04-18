
# export JAVA_HOME=/opt/deploy/jdk1.6.0_24
export JAVA_HOME=/cygdrive/c/java/jdk1.7.0_11

CLOJURE=./*
CLOJURE=$CLOJURE:./clojure-1.4.0.jar
CLOJURE=$CLOJURE:./java.jdbc-0.2.3.jar
CLOJURE=$CLOJURE:./ojdbc-6.jar
CLOJURE=./src:$CLOJURE

export CLOJURE

# clojure-1.4.0.jar  java.jdbc-0.2.3.jar  ojdbc-6.jar
$JAVA_HOME/bin/java -cp $CLOJURE clojure.main $*
