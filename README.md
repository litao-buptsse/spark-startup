# Spark Startup - Build Your First Spark APP

[![Build Status](https://travis-ci.org/litao-buptsse/spark-startup.svg?branch=master)](https://travis-ci.org/litao-buptsse/spark-startup)
[![Software License](https://img.shields.io/badge/license-Apache%202.0-brightgreen.svg)](https://github.com/litao-buptsse/spark-startup/blob/master/LICENSE)
[![Project Status](https://stillmaintained.com/litao-buptsse/spark-startup.png)](https://stillmaintained.com/litao-buptsse/spark-startup)

---

## Requirements

* JDK-1.7
* [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)

## Building


### 1. docker build

```
$ cd docker; make
```

* docker image: registry.docker.dev.sogou-inc.com:5000/clouddev/spark-startup:1.0

### 2. release tgz

```
$ bin/release-tgz.sh
```

* tgz location: dist/spark-startup_2.10.4-1.0.tgz


### 3. sbt package

```
$ build/sbt clean package 
```

* jar location: target/scala-2.10/spark-startup_2.10-1.0.jar

### 4. sbt assembly

```
$ build/sbt clean assembly 
```

* assembly jar location: target/scala-2.10/spark-startup-assembly-1.0.jar


## Configuration

* config file location: conf/application.conf

## Running

### 1. docker_run.sh

```
$ mkdir app; cp docker/docker_run.sh app/; cp conf/* app/
$ cd app/; ./docker_run.sh <input>
```

### 2. run.sh

```
$ tar -xzvf spark-startup-scala_2.10.4-1.0.tgz
$ cd spark-startup-scala_2.10.4-1.0; ./run.sh <input>
```

## Resources

### About Spark
Please refer to the [spark programming guide](http://spark.apache.org/docs/latest/programming-guide.html) for more informatioin

### About Scala

Please refer to [scala documentation](http://www.scala-lang.org/documentation/) the for more informatioin

### About SBT
Please refer to the [sbt tutorial](http://www.scala-sbt.org/0.13/tutorial/index.html) for more informatioin

### About Typesafe Config
Please refer to the [typesafehub/config](https://github.com/typesafehub/config) for more informatioin

### About Docker
Please refer to the [docker docks](https://docs.docker.com/) for more informatioin