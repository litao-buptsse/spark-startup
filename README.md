# Spark Startup - Build Your First Spark APP

---

## SBT���

```
$ build/sbt package
```

Note: 

1. ������JARλ�ã�target/scala-2.10/spark-startup-scala_2.10-1.0.jar
2. ����ʹ��build/sbt���߽��д�������Զ�����scala��sbt��zinc�ȹ��ߣ����Զ�����zinc���ٱ���

## ��������

```
$ spark-submit \
  	--master local \
  	--class com.sogou.spark.HelloSparkCore \
  	spark-startup.jar /user/spark/README.md
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
  	spark-startup.jar /user/spark/README.md
```