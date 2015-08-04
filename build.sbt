name := "spark-startup"

version := "1.0"

scalaVersion := "2.10.4"

autoScalaLibrary := false

resolvers ++= Seq(
  "Maven Repository" at "http://repo1.maven.org/maven/",
  "Apache Repository" at "https://repository.apache.org/content/repositories/releases",
  "JBoss Repository" at "https://repository.jboss.org/nexus/content/repositories/releases",
  "MQTT Repository" at "https://repo.eclipse.org/content/repositories/paho-releases",
  "Cloudera Repository" at "https://repository.cloudera.com/artifactory/cloudera-repos",
  "MapR Repository" at "http://repository.mapr.com/maven",
  "spring-releases" at "https://repo.spring.io/libs-release",
  "Job Server Bintray" at "http://dl.bintray.com/spark-jobserver/maven",
  "Sogou Maven Repository" at "http://cloud.sogou-inc.com/nexus/content/groups/public"
)

libraryDependencies ++= {
  val sparkVersion = "1.4.0"
  val sparkJobServerVersion = "0.5.1"
  val hbaseVersion = "0.98.13-hadoop2"
  val excludeSpark = ExclusionRule(organization = "org.apache.spark")
  val excludeHbase = ExclusionRule(organization = "org.apache.hbase")
  val excludeScalaLang = ExclusionRule(organization = "org.scala-lang")
  val excludeScalaTest = ExclusionRule(organization = "org.scalatest")
  val excludeNetty = ExclusionRule(organization = "io.netty")
  val excludeJson4s = ExclusionRule(organization = "org.json4s")
  val excludeSlf4j = ExclusionRule(organization = "org.slf4j")
  val excludeLog4j = ExclusionRule(organization = "log4j")
  val excludeSparkProject = ExclusionRule(organization = "org.spark-project.spark")
  Seq(
    "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
    "org.apache.spark" %% "spark-mllib" % sparkVersion % "provided",
    "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
    "org.apache.spark" %% "spark-hive" % sparkVersion % "provided",
    "org.apache.spark" %% "spark-streaming" % sparkVersion % "provided",
    "org.apache.spark" %% "spark-streaming-kafka" % sparkVersion
      excludeAll(excludeSparkProject, excludeScalaLang, excludeSlf4j, excludeLog4j),
    "org.apache.hbase" % "hbase-common" % hbaseVersion % "provided",
    "org.apache.hbase" % "hbase-client" % hbaseVersion % "provided",
    "org.apache.hbase" % "hbase-server" % hbaseVersion % "provided",
    "com.github.fommil.netlib" % "all" % "1.1.2" pomOnly(),
    "com.cloudera" % "spark-hbase" % "0.0.2-clabs"
      excludeAll(excludeSpark, excludeHbase, excludeScalaLang, excludeScalaTest),
    "eu.unicredit" %% "hbase-rdd" % "0.5.2"
      excludeAll(excludeSpark, excludeHbase, excludeScalaLang, excludeJson4s),
    "spark.jobserver" %% "job-server-api" % sparkJobServerVersion % "provided"
      excludeAll(excludeSpark, excludeScalaLang, excludeScalaTest, excludeNetty),
    "spark.jobserver" %% "job-server-extras" % sparkJobServerVersion % "provided"
      excludeAll(excludeSpark, excludeScalaLang, excludeScalaTest, excludeNetty)
  )
}

mainClass in assembly := Some("com.sogou.spark.HelloSparkCore")