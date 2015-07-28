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
  "Sogou Maven Repository" at "http://cloud.sogou-inc.com/nexus/content/groups/public"
)

libraryDependencies ++= {
  val sparkVersion = "1.4.0"
  val hbaseVersion = "0.98.13-hadoop2"
  Seq(
    "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
    "org.apache.spark" %% "spark-mllib" % sparkVersion % "provided",
    "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
    "org.apache.spark" %% "spark-hive" % sparkVersion % "provided",
    "org.apache.spark" %% "spark-streaming" % sparkVersion % "provided",
    "org.apache.spark" %% "spark-streaming-kafka" % sparkVersion
      exclude("org.spark-project.spark", "unused")
      exclude("org.scala-lang", "scala-library")
      exclude("org.slf4j", "slf4j-api")
      exclude("log4j", "log4j"),
    "org.apache.hbase" % "hbase-common" % hbaseVersion % "provided",
    "org.apache.hbase" % "hbase-client" % hbaseVersion % "provided",
    "org.apache.hbase" % "hbase-server" % hbaseVersion % "provided",
    "com.github.fommil.netlib" % "all" % "1.1.2" pomOnly(),
    "com.cloudera" % "spark-hbase" % "0.0.2-clabs" excludeAll(
      ExclusionRule(organization = "org.apache.hbase"),
      ExclusionRule(organization = "org.apache.spark"),
      ExclusionRule(organization = "org.scala-lang"),
      ExclusionRule(organization = "org.scalatest")
      ),
    "eu.unicredit" %% "hbase-rdd" % "0.5.2" excludeAll(
      ExclusionRule(organization = "org.apache.hbase"),
      ExclusionRule(organization = "org.apache.spark"),
      ExclusionRule(organization = "org.scala-lang"),
      ExclusionRule(organization = "org.json4s")
      )
  )
}

mainClass in assembly := Some("com.sogou.spark.HelloSparkCore")