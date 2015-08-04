package com.sogou.spark

import com.typesafe.config.Config
import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.hive.HiveContext
import spark.jobserver._

import scala.util.Try

/**
 * Created by Tao Li on 8/5/15.
 */
object HelloSparkJobServer extends SparkJob {

  override def runJob(sc: SparkContext, config: Config): Any = {
    sc.parallelize(config.getString("input.string").split(" ").toSeq).map((_, 1)).reduceByKey(_ + _).collect().toMap
  }

  override def validate(sc: SparkContext, config: Config): SparkJobValidation = {
    Try(config.getString("input.string"))
      .map(x => SparkJobValid)
      .getOrElse(SparkJobInvalid("No input.string config param"))
  }
}

object SaveNamedRDD extends SparkJob with NamedRddSupport {
  override def runJob(sc: SparkContext, config: Config): Any = {
    val rdd = sc.parallelize(config.getString("input.string").split(" ").toSeq).map((_, 1)).reduceByKey(_ + _)
    namedRdds.update("WordCounts", rdd)
  }

  override def validate(sc: SparkContext, config: Config): SparkJobValidation = {
    Try(config.getString("input.string"))
      .map(x => SparkJobValid)
      .getOrElse(SparkJobInvalid("No input.string config param"))
  }
}

object QueryNamedRDD extends SparkJob with NamedRddSupport {
  override def runJob(sc: SparkContext, config: Config): Any = {
    namedRdds.get("WordCounts").get.collect().toMap
  }

  override def validate(sc: SparkContext, config: Config): SparkJobValidation = SparkJobValid
}

object HelloSQLContextJob extends SparkSqlJob {
  override def runJob(sc: SQLContext, config: Config): Any = {
    val logdate = config.getString("logdate")
    sc.sql(s"select channel, count(*) from custom.common_pc_pv where logdate='$logdate' group by channel").collect()
  }

  override def validate(sc: SQLContext, config: Config): SparkJobValidation = {
    Try(config.getString("logdate"))
      .map(x => SparkJobValid)
      .getOrElse(SparkJobInvalid("No logdate config param"))
  }
}

object HelloHiveContextJob extends SparkHiveJob {
  override def runJob(sc: HiveContext, config: Config): Any = {
    val logdate = config.getString("logdate")
    sc.sql(s"select channel, count(*) from custom.common_pc_pv where logdate='$logdate' group by channel").collect()
  }

  override def validate(sc: HiveContext, config: Config): SparkJobValidation = {
    Try(config.getString("logdate"))
      .map(x => SparkJobValid)
      .getOrElse(SparkJobInvalid("No logdate config param"))
  }
}