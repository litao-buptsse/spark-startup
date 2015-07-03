package com.sogou.spark

import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by Tao Li on 2015/7/3.
 */
object HelloSparkCore {
  def main(args: Array[String]): Unit = {
    if (args.length != 1) {
      println("usage: spark-submit --master yarn-client " +
        "--class com.sogou.spark.HelloSparkCore spark-startup.jar <filename>")
      System.exit(1)
    }

    val fileName = args(0)
    val conf = new SparkConf().setAppName("HelloSparkCore")
    val sc = new SparkContext(conf)
    sc.textFile(fileName).flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)
      .collect.foreach(println)
  }
}
