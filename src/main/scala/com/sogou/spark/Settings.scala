package com.sogou.spark

import com.typesafe.config.{Config, ConfigFactory, ConfigValue}

/**
 * Created by Tao Li on 9/30/15.
 */
class Settings(config: Config) extends Serializable {
  config.checkValid(ConfigFactory.defaultReference(), "app")

  val SPARK_APP_NAME = config.getString("app.spark.appName")

  import scala.collection.JavaConversions._

  val sparkConfigMap = if (config.hasPath("app.spark.passthrough")) {
    config.getConfig("app.spark.passthrough").root map { case (key: String, cv: ConfigValue) =>
      (key, cv.atPath(key).getString(key))
    }
  } else {
    Map.empty[String, String]
  }
}