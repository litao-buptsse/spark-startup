package com.sogou.spark

import org.apache.hadoop.hbase.client.HBaseAdmin
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.{HBaseConfiguration, HTableDescriptor, TableName}
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by Tao Li on 7/21/15.
 */
object HelloHbase {
  def main(args: Array[String]) {
    val tableName = args(0)

    val sparkConf = new SparkConf().setAppName("HelloHbase")
    val sc = new SparkContext(sparkConf)
    val conf = HBaseConfiguration.create()
    conf.set(TableInputFormat.INPUT_TABLE, tableName)

    val hBaseRDD = sc.newAPIHadoopRDD(conf, classOf[TableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result])

    hBaseRDD.count()

    sc.stop()
  }
}
