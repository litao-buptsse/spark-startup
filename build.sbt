name := "spark-startup-scala"

version := "1.0"

scalaVersion := "2.10.4"

libraryDependencies ++= {
  val sparkVersion = "1.4.0"
  Seq(
    "org.apache.spark" %% "spark-core" % sparkVersion
  )
}