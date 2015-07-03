# Spark Startup - Build Your First Spark APP

---

## 编译环境、开发工具

1. JDK-1.7
2. [IntelliJ IDEA（推荐）](https://www.jetbrains.com/idea/download/)

## SBT打包

```
$ build/sbt package
```

Note: 

1. 默认版本：scala(2.10.4)、sbt(0.13.5)、spark(1.4.0)
1. 打包后的JAR位置：target/scala-2.10/spark-startup-scala_2.10-1.0.jar
2. 建议使用build/sbt工具进行打包，将自动下载scala、sbt、zinc等工具，并自动开启zinc加速编译

## 本地运行

```
$ spark-submit \
  	--master local \
  	--class com.sogou.spark.HelloSparkCore \
  	target/scala-2.10/spark-startup-scala_2.10-1.0.jar /user/spark/README.md
```

## 集群运行

```
$ spark-submit \
  	--master yarn-client \
  	--class com.sogou.spark.HelloSparkCore \
  	--executor-memory 512M \
  	--driver-memory 512M \
  	--num-executors 2 \
  	--executor-cores 1 \
  	--queue root.spark_test \
  	target/scala-2.10/spark-startup-scala_2.10-1.0.jar /user/spark/README.md
```