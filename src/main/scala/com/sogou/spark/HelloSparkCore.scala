/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.sogou.spark

import com.typesafe.config.ConfigFactory
import org.apache.spark.{SparkContext, SparkConf}
import org.slf4j.{LoggerFactory, Logger}

/**
 * Created by Tao Li on 2015/7/3.
 */
object HelloSparkCore extends App {
  val LOG: Logger = LoggerFactory.getLogger(getClass)

  if (args.length != 1) {
    LOG.error("usage: spark-submit --master yarn-client " +
      "--class com.sogou.spark.HelloSparkCore spark-startup.jar <filename>")
    System.exit(1)
  }

  val fileName = args(0)

  val config = ConfigFactory.load()
  val settings = new Settings(config)

  val conf = new SparkConf().setAppName(settings.SPARK_APP_NAME)
  for ((k, v) <- settings.sparkConfigMap) conf.set(k, v)
  val sc = new SparkContext(conf)

  sc.textFile(fileName).flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)
    .collect.foreach(println)
}
