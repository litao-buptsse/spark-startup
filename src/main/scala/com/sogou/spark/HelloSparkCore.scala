package com.sogou.spark

import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by Tao Li on 2015/7/3.
 */
object HelloSparkCore {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("HelloSparkCore").setMaster("local[*]")
    val sc = new SparkContext(conf)
    sc.textFile("README.md")
      .flatMap(_.split(" ")).map((_, 1))
      .reduceByKey(_ + _)
      .collect.foreach(println)
  }
}
