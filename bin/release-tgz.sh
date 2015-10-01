#!/bin/bash

dir=`dirname $0`
rootDir=`cd $dir/..; pwd`

cd $rootDir
$rootDir/build/sbt clean assembly
ret=$?; [ $ret != 0 ] && exit $ret

name=`cat build.sbt | grep name | head -n 1 | awk -F"\"" '{print $2}'`
version=`cat build.sbt | grep version | head -n 1 | awk -F"\"" '{print $2}'`
scalaVersion=`cat build.sbt | grep scalaVersion | head -n 1 | awk -F"\"" '{print $2}'`
tgzName=${name}_${scalaVersion}-$version

tmpDir=$rootDir/.tmp; mkdir -p $tmpDir; cd $tmpDir
tmpDistDir=$tmpDir/$tgzName; mkdir -p $tmpDistDir

cp $rootDir/target/scala-2.10/*-assembly-*.jar $tmpDistDir
cp $rootDir/bin/run.sh $tmpDistDir
cp $rootDir/conf/* $tmpDistDir
tar -zcvf $tgzName.tgz $tgzName

distDir=$rootDir/dist; mkdir -p $distDir
rm -fr $distDir/$tgzName.tgz
mv $tmpDir/$tgzName.tgz $distDir

rm -fr $tmpDir
