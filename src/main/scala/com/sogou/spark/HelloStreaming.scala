package com.sogou.spark

import org.apache.spark.{HashPartitioner, SparkConf}
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * Created by Tao Li on 7/15/15.
 */
object HelloStreaming {
  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount")
    val ssc = new StreamingContext(conf, Seconds(10))

    val lines = ssc.socketTextStream("localhost", 9999)

    // Count each word in each batch
    val wordCounts = lines.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)

    // Print the first ten elements of each RDD generated in this DStream to the console
    wordCounts.print

    // Start the computation
    ssc.start
    // Wait for the computation to terminate
    ssc.awaitTermination
  }
}

object HelloKafkaSource {
  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local[2]").setAppName("HelloKafkaSource")
    val ssc = new StreamingContext(conf, Seconds(1))

    val KAFKA_ZOOKEEPER_QUORUM = "10.11.204.97:2181,10.11.204.98:2181,10.11.204.99:2181/kafka"
    val KAFKA_CONSUMER_GROUP = "realtime-alert"
    val KAFKA_TOPICS = "clouddev.monitoring.service"
    val KAFKA_CONSUMER_THREAD_NUM = 5
    val topicMap = KAFKA_TOPICS.split(",").map((_, KAFKA_CONSUMER_THREAD_NUM)).toMap

    val inputStream: DStream[(String, String)] = KafkaUtils.createStream(ssc,
      KAFKA_ZOOKEEPER_QUORUM, KAFKA_CONSUMER_GROUP, topicMap)

    val riskData = inputStream.filter { kv =>
      val value = kv._2.toLong
      value > 10000
    }
  }
}

object HelloUpdateStateByKey {
  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local[2]").setAppName("HelloUpdateStateByKey")
    val ssc = new StreamingContext(conf, Seconds(5))
    ssc.checkpoint("cp")

    var updateFunc = (values: Seq[Int], state: Option[Int]) => Some(values.sum + state.getOrElse(0))

    val lines = ssc.socketTextStream("localhost", 9999)

    val wordDstream = lines.flatMap(_.split(" ")).map((_, 1))

    val stateDstream = wordDstream.updateStateByKey[Int](updateFunc)
    stateDstream.print()

    ssc.start
    ssc.awaitTermination
  }
}

object HelloTransform {
  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local[2]").setAppName("HelloTransform")
    val ssc = new StreamingContext(conf, Seconds(1))

    val lines = ssc.socketTextStream("localhost", 9999)

    val wordCounts = lines.transform(rdd =>
      rdd.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)
    )
    wordCounts.print

    ssc.start
    ssc.awaitTermination()
  }
}

object HelloWindow {
  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local[2]").setAppName("HelloTransform")
    val ssc = new StreamingContext(conf, Seconds(1))

    val lines = ssc.socketTextStream("localhost", 9999)

    val wordCounts = lines.flatMap(_.split(" ")).map((_, 1))
      .reduceByKeyAndWindow((a: Int, b: Int) => (a + b), Seconds(5), Seconds(1))

    wordCounts.print

    ssc.start
    ssc.awaitTermination()
  }
}

object HelloWindow2 {
  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local[2]").setAppName("HelloTransform")
    val ssc = new StreamingContext(conf, Seconds(1))

    val lines = ssc.socketTextStream("localhost", 9999)

    val wordCounts = lines.window(Seconds(5), Seconds(1)).flatMap(_.split(" "))
      .map((_, 1)).reduceByKey(_ + _)

    wordCounts.print

    ssc.start
    ssc.awaitTermination()
  }
}

object HelloForeach {
  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local[2]").setAppName("HelloTransform")
    val ssc = new StreamingContext(conf, Seconds(1))

    val lines = ssc.socketTextStream("localhost", 9999)

    val wordCounts = lines.window(Seconds(5), Seconds(1)).flatMap(_.split(" "))
      .map((_, 1)).reduceByKey(_ + _)


    wordCounts.foreachRDD(rdd =>
      rdd.foreach(println)
    )

    wordCounts.foreachRDD(rdd =>
      rdd.foreachPartition(partitionOfRecords =>
        partitionOfRecords.foreach(println)
      )
    )

    ssc.start
    ssc.awaitTermination()
  }
}


object HelloCache {
  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local[2]").setAppName("HelloCache")
    val ssc = new StreamingContext(conf, Seconds(1))

    val lines = ssc.socketTextStream("localhost", 9999)

    val wordCounts = lines.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)

    wordCounts.persist()

    wordCounts.print

    ssc.start
    ssc.awaitTermination
  }
}

object HelloCheckpointing {
  def main(args: Array[String]) {
    val checkpointDirectory = "cp"

    var updateFunc = (values: Seq[Int], state: Option[Int]) => Some(values.sum + state.getOrElse(0))

    def functionToCreateContext(): StreamingContext = {
      val conf = new SparkConf().setMaster("local[2]").setAppName("HelloCheckpointing")
      val ssc = new StreamingContext(conf, Seconds(1))
      ssc.checkpoint(checkpointDirectory)

      val lines = ssc.socketTextStream("localhost", 9999)
      val wordDstream = lines.flatMap(_.split(" ")).map((_, 1))
      val stateDstream = wordDstream.updateStateByKey[Int](updateFunc)
      stateDstream.print()

      ssc
    }

    val ssc = StreamingContext.getOrCreate(checkpointDirectory, functionToCreateContext)

    ssc.start
    ssc.awaitTermination
  }
}