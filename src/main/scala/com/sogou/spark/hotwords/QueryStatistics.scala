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

    implicit val longWriter = new Writes[Long] {
      def write(data: Long) = Bytes.toBytes(data)
    }

    val rdd: RDD[(String, Map[String, (Long, Long)])] = sqlContext.sql( s"""
    SELECT pid, query, count(*) AS pv
    FROM custom.scribe_common_wap_nginx
    WHERE logdate='$logdate' AND clean_state='OK' AND pid != '' AND query != ''
    GROUP BY pid, query
    LIMIT 20
      """).map(r =>
      (r.getAs[String]("pid"), (r.getAs[String]("query"), (r.getAs[Long]("pv"), logdate.toLong)))
      ).groupByKey.mapValues(_.toMap)

    implicit val config = HBaseConfig()
    val table = "hotwords_demo"
    val cf = "query"

    rdd.toHBaseBulk(table, cf)
    // rdd.toHBase(table, cf)
  }
}
