#!/bin/bash

LIB_DIR=./lib

LOCAL_PREFIX=""
PATTERN=$LIB_DIR/*.jar
for i in $PATTERN; do
    if [ "$i" != "$PATTERN" ]; then
    	JAR=$LOCAL_PREFIX$i
    	WIN_JAR=$(echo $LOCAL_PREFIX$i | sed 's#./lib/#.\\lib\\#')
      UNIX_CLASSPATH=$UNIX_CLASSPATH,$JAR
      WIN_CLASSPATH=$WIN_CLASSPATH,$WIN_JAR
  fi
done

UNIX_CLASSPATH=`echo $UNIX_CLASSPATH | cut -c2-`
echo "UNIX_CLASSPATH: " $UNIX_CLASSPATH
WIN_CLASSPATH=`echo $WIN_CLASSPATH | cut -c2-`
echo "WIN_CLASSPATH: " $WIN_CLASSPATH
