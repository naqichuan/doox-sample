#!/bin/bash

if [ -z "$JAVA_HOME" -a -z "$JRE_HOME" ]; then
  if $darwin; then
    if [ -x '/usr/libexec/java_home' ] ; then
      export JAVA_HOME=`/usr/libexec/java_home`
    elif [ -d "/System/Library/Frameworks/JavaVM.framework/Versions/CurrentJDK/Home" ]; then
      export JAVA_HOME="/System/Library/Frameworks/JavaVM.framework/Versions/CurrentJDK/Home"
    fi
  else
    JAVA_PATH=`which java 2>/dev/null`
    if [ "x$JAVA_PATH" != "x" ]; then
      JAVA_PATH=`dirname $JAVA_PATH 2>/dev/null`
      JRE_HOME=`dirname $JAVA_PATH 2>/dev/null`
    fi
    if [ "x$JRE_HOME" = "x" ]; then
      if [ -x /usr/bin/java ]; then
        JRE_HOME=/usr
      fi
    fi
  fi
  if [ -z "$JAVA_HOME" -a -z "$JRE_HOME" ]; then
    echo "Neither the JAVA_HOME nor the JRE_HOME environment variable is defined"
    echo "At least one of these environment variable is needed to run this program"
    exit 1
  fi
fi
if [ -z "$JAVA_HOME" -a "$1" = "debug" ]; then
  echo "JAVA_HOME should point to a JDK in order to run in debug mode."
  exit 1
fi
if [ -z "$JRE_HOME" ]; then
  JRE_HOME="$JAVA_HOME"
fi

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

[ -z "$WORKER_HOME" ] && WORKER_HOME=`cd "$PRGDIR/.." >/dev/null; pwd`

export worker_home="$WORKER_HOME"

CLASSPATH=
for i in "$WORKER_HOME"/lib/*.jar; do
   CLASSPATH="$CLASSPATH":"$i"
done

#java虚拟机启动参数
#JAVA_OPTS="-Xms1024M -Xmx1024M -Xmn256M -Djava.awt.headless=true -verbose:gc -XX:+PrintGCDetails -Xloggc:gc.log"
JAVA_OPTS="-Xms1024M -Xmx1024M -Xmn256M"

WORKER_MAINCLASS=nqcx.worker.startup.Bootstrap

$JAVA_HOME/bin/java $JAVA_OPTS -classpath $CLASSPATH $WORKER_MAINCLASS
