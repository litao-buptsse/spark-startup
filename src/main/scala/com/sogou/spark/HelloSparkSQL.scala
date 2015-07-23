package com.sogou.spark

import java.sql.DriverManager

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

object HelloThriftServer {
  def main(args: Array[String]) {
    Class.forName("org.apache.hive.jdbc.HiveDriver")
    val con = DriverManager.getConnection("jdbc:hive2://10.11.214.224:10000/default", "tom", "123456")
    val stmt = con.createStatement()
    val sql = "SELECT channel, count(*) AS pv FROM custom.common_pc_pv WHERE logdate='2015072105' GROUP BY channel"
    val res = stmt.executeQuery(sql)
    while (res.next()) {
      println(s"${res.getString("channel")}, ${res.getString("pv")}")
    }
    con.close()
  }
}