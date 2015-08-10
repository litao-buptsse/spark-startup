package com.sogou.spark.hotwords

import org.apache.hadoop.hbase.util.Bytes
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}
import unicredit.spark.hbase._

/**
 * Created by Tao Li on 8/10/15.
 */
object QueryStatistics {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("QueryStatistics")
    val sc = new SparkContext(conf)
    val sqlContext = new HiveContext(sc)

    val logdate = args(0)

    val rdd: RDD[(String, Map[String, (Array[Byte], Long)])] = sqlContext.sql( s"""
    SELECT pid, query, count(*) AS pv
    FROM custom.scribe_common_wap_nginx
    WHERE logdate='$logdate' AND clean_state='OK' AND pid != '' AND query != ''
    GROUP BY pid, query
    LIMIT 20
      """).map(r =>
      (r.getAs[String]("pid"), (r.getAs[String]("query"), (Bytes.toBytes(r.getAs[Long]("pv")), logdate.toLong)))
      ).groupByKey.map(r => (r._1, r._2.toMap))

    implicit val config = HBaseConfig()
    val table = "hotwords_demo"
    val cf = "query"

    // rdd.toHBaseBulk(table, cf)
    rdd.toHBase(table, cf)
  }
}
