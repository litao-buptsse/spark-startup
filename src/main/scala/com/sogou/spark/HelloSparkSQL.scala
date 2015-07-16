package com.sogou.spark

import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.sql.SQLContext

/**
 * Created by Tao Li on 7/16/15.
 */
object HelloSparkSQL {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("HelloSparkSQL")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    val df = sqlContext.sql(
      "select channel, count(*) as pv from custom.common_pc_pv where logdate='2015071605' group by channel");
    df.show
  }
}
