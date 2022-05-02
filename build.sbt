name := "database-app"

version := "1.0"

scalaVersion := "2.13.1"

// Run in a separate JVM, to make sure sbt waits until all threads have
// finished before returning.
// If you want to keep the application running while executing other
// sbt tasks, consider https://github.com/spray/sbt-revolver/
fork := true

libraryDependencies ++= Seq(
  "org.postgresql" % "postgresql" % "42.3.4",
  "org.scalatest" %% "scalatest" % "3.1.0" % Test
)
