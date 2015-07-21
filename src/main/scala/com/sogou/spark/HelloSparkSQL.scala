package com.sogou.spark

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by Tao Li on 7/16/15.
 */
object HelloHive {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("HelloHive")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    val sql = "select channel, count(*) as pv from custom.common_pc_pv " +
      "where logdate='2015071605' group by channel"
    val df = sqlContext.sql(sql)

    // Save to hdfs
    df.rdd.saveAsTextFile("pv_info")
    // Print to console
    df.show()
  }
}
