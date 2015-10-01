#!/bin/bash

if [ $# -ne 1 ]
then
    echo "usage: $0 <input>"
    exit 1
fi

input=$1

export SPARK_CLASSPATH=.:$SPARK_CLASSPATH

spark-submit \
    --master yarn-client \
    --jars application.conf,log4j.properties \
    --executor-memory 512M \
    --driver-memory 512M \
    --num-executors 2 \
    --executor-cores 1 \
    --queue root.slave \
    spark-startup-assembly-1.0.jar $input