package com.sogou.spark

import com.cloudera.spark.hbase.HBaseContext
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.Scan
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by Tao Li on 7/21/15.
 */
object HelloHbase {
  def main(args: Array[String]) {
    val tableName = "spark-test"
    val hbaseConf = HBaseConfiguration.create()
    hbaseConf.set(TableInputFormat.INPUT_TABLE, tableName)

    val sparkConf = new SparkConf().setAppName("HelloHbase")
    val sc = new SparkContext(sparkConf)

    val hBaseRDD = sc.newAPIHadoopRDD(hbaseConf, classOf[TableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result])

    val total = hBaseRDD.count()
    println(s"total: $total")

    sc.stop()
  }
}

object HelloSparkOnHbase {
  def main(args: Array[String]) {
    val tableName = "spark-test"
    val hbaseConf = HBaseConfiguration.create()

    var scan = new Scan()
    scan.addFamily(Bytes.toBytes("query"))
    scan.addFamily(Bytes.toBytes("topic"))
    scan.setMaxVersions(3)
    scan.setCaching(100)

    val sparkConf = new SparkConf().setAppName("HelloSparkOnHbase")
    val sc = new SparkContext(sparkConf)
    val hbaseContext = new HBaseContext(sc, hbaseConf)

    var getRdd = hbaseContext.hbaseRDD(tableName, scan)
    getRdd.collect.foreach { v =>
      val row = Bytes.toString(v._1)
      for (i <- 0 until v._2.size()) {
        val column = v._2.get(i)
        val family = Bytes.toString(column._1)
        val qualifier = Bytes.toString(column._2)
        val value = Bytes.toString(column._3)
        println(s"$row, $family, $qualifier, $value")
      }
    }
  }
}