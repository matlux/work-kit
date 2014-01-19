#!/bin/sh

# resolve links - $0 may be a softlink
PRG="$0"

while [ -h "$PRG" ]; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

PRGDIR=`dirname "$PRG"`
BASEDIR=`cd "$PRGDIR" >/dev/null; pwd`



#echo BASEDIR=$BASEDIR
if [ ! -d "${BASEDIR}/lib" ]
then
	mkdir ${BASEDIR}/lib
fi
OLDPWD=`pwd`
CURDIR=`pwd`
cd ${BASEDIR}/lib
if [ ! -f "${BASEDIR}/lib/clojure-1.4.0.jar" ]
then
        wget http://nexushost/nexus/service/local/repo_groups/releases/content/org/clojure/clojure/1.4.0/clojure-1.4.0.jar
	wget http://nexushost/nexus/service/local/repo_groups/releases/content/fs/fs/1.3.3/fs-1.3.3.jar
	wget http://nexushost/nexus/service/local/repo_groups/releases/content/clj-http-lite/clj-http-lite/0.2.0/clj-http-lite-0.2.0.jar
fi
cd ${OLDPWD}

CLASSPATH=${BASEDIR}/lib/clojure-1.4.0.jar
CLASSPATH=${BASEDIR}/lib/fs-1.3.3.jar:$CLASSPATH
CLASSPATH=${BASEDIR}/lib/clj-http-lite-0.2.0.jar:$CLASSPATH
CLASSPATH=${BASEDIR}/fleet-0.9.5.jar:$CLASSPATH
CLASSPATH=${BASEDIR}/src:$CLASSPATH
CLASSPATH=${BASEDIR}:$CLASSPATH
CLASSPATH=${CURDIR}:$CLASSPATH
CLASSPATH=${CURDIR}/deploy:$CLASSPATH

export CLASSPATH

echo $JAVA_HOME/bin/java -cp $CLASSPATH clojure.main $*
$JAVA_HOME/bin/java -cp $CLASSPATH clojure.main $*
