package com.sogou.spark

import java.sql.DriverManager

import org.apache.spark.sql.{SQLContext, SaveMode}
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

object HelloORC {

  import org.apache.spark.sql.hive._

  case class Contact(name: String, phone: String)

  case class Person(name: String, age: Int, contacts: Seq[Contact])

  def main(args: Array[String]) {

    val conf = new SparkConf().setAppName("HelloORC")
    val sc = new SparkContext(conf)
    val sqlContext = new HiveContext(sc)

    sqlContext.setConf("spark.sql.orc.filterPushdown", "true")

    import sqlContext.implicits._

    val records = (1 to 100).map { i =>
      Person(s"name_$i", i, (0 to 1).map { m => Contact(s"contact_$m", s"phone_$m")})
    }

    val namenode = "hdfs://SunshineNameNode2"
    val hiveRoot = "/user/hive/warehouse"
    val database = "user_tmp"
    val table = "people"
    val logdate = "2015072810"
    val path = s"$hiveRoot/$database.db/$table/logdate=$logdate"

    // 1. save orc to hdfs
    sc.parallelize(records).toDF().write.format("orc").mode(SaveMode.Append).save(s"$namenode$path")
    // 2. create table if not exists
    sqlContext.sql(s"CREATE TABLE IF NOT EXISTS $database.$table (name string, age int, contacts array<struct<name:string,phone:string>>) PARTITIONED BY (logdate string) STORED AS orc")
    sqlContext.sql(s"use $database")
    // 3. alter table add partition
    sqlContext.sql(s"ALTER TABLE $table ADD PARTITION(logdate='$logdate') LOCATION '$path'")
    // 4. query table
    sqlContext.sql(s"SELECT name FROM $database.$table WHERE logdate='$logdate' AND age>50").show
  }
}