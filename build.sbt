name := "spark-startup-scala"

version := "1.0"

scalaVersion := "2.10.4"

autoScalaLibrary := false

resolvers ++= Seq(
  "Central Maven Repository" at "http://repo1.maven.org/maven/",
  "Sogou Maven Repository" at "http://cloud.sogou-inc.com/nexus/content/groups/public"
)

libraryDependencies ++= {
  val sparkVersion = "1.4.0"
  Seq(
    "org.apache.spark" %% "spark-core" % sparkVersion % "provided"
  )
}

mainClass in assembly := Some("com.sogou.spark.HelloSparkCore")