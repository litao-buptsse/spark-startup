name := "spark-startup-scala"

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
  val sparkVersion = "1.5.0"
  Seq(
    "com.typesafe" % "config" % "1.2.1" % "provided",
    "org.apache.spark" %% "spark-core" % sparkVersion % "provided"
  )
}

mainClass in assembly := Some("com.sogou.spark.HelloSparkCore")