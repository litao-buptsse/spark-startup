#!/bin/bash

if [ $# -ne 1 ]
then
    echo "usage: $0 <input>"
    exit 1
fi

input=$1

dir=`dirname $0`
dir=`cd $dir; pwd`

REGISTRY=registry.docker.dev.sogou-inc.com:5000
IMAGE=clouddev/spark-startup
VERSION=1.0

CONFIG_FILE=$dir/application.conf

mkdir -p $LOG_DIR

docker run --rm --net=host \
    -v $CONFIG_FILE:/search/app/application.conf \
    -v /root/ugi_config:/root/ugi_config \
    $REGISTRY/$IMAGE:$VERSION /search/app/run.sh $input