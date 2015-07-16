package com.sogou.spark

import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.mllib.recommendation.ALS
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel
import org.apache.spark.mllib.recommendation.Rating

/**
 * Created by Tao Li on 7/15/15.
 */
object HelloALS {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("HelloALS")
    val sc = new SparkContext(conf)

    val data = sc.textFile("/user/garysong/spark_proj/mid_openid_readnum_format")
    val ratings = data.map(_.split(',') match { case Array(user, item, rate) =>
      Rating(user.toInt, item.toInt, rate.toDouble)
    })

    val rank = 500
    val numIterations = 20
    val model = ALS.train(ratings, rank, numIterations, 0.01)

    model.save(sc, "myModelPath")
  }
}
