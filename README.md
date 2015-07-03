# Spark Startup - Build Your First Spark APP

---

## ���뻷������������

1. JDK-1.7
2. [IntelliJ IDEA���Ƽ���](https://www.jetbrains.com/idea/download/)

## SBT���

```
$ build/sbt package
```

Note: 

1. Ĭ�ϰ汾��scala(2.10.4)��sbt(0.13.5)��spark(1.4.0)
1. ������JARλ�ã�target/scala-2.10/spark-startup-scala_2.10-1.0.jar
2. ����ʹ��build/sbt���߽��д�������Զ�����scala��sbt��zinc�ȹ��ߣ����Զ�����zinc���ٱ���

## ��������

```
$ spark-submit \
  	--master local \
  	--class com.sogou.spark.HelloSparkCore \
  	target/scala-2.10/spark-startup-scala_2.10-1.0.jar /user/spark/README.md
```

## ��Ⱥ����

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