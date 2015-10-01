package com.sogou.spark

import com.typesafe.config.ConfigFactory
import org.apache.spark.{SparkContext, SparkConf}
import org.slf4j.{LoggerFactory, Logger}

/**
 * Created by Tao Li on 2015/7/3.
 */
object HelloSparkCore extends App {
  val LOG: Logger = LoggerFactory.getLogger(getClass)

  if (args.length != 1) {
    LOG.error("usage: spark-submit --master yarn-client " +
      "--class com.sogou.spark.HelloSparkCore spark-startup.jar <filename>")
    System.exit(1)
  }

  val fileName = args(0)

  val config = ConfigFactory.load()
  val settings = new Settings(config)

  val conf = new SparkConf().setAppName(settings.SPARK_APP_NAME)
  for ((k, v) <- settings.sparkConfigMap) conf.set(k, v)
  val sc = new SparkContext(conf)

  sc.textFile(fileName).flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)
    .collect.foreach(println)
}
