name := """play-hands-on"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.2"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.0" % Test

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
libraryDependencies += jdbc
libraryDependencies += evolutions
libraryDependencies ++= Seq(
  "com.h2database"  %  "h2"                           % "1.4.196", // your jdbc driver here
  "org.scalikejdbc" %% "scalikejdbc"                  % "3.0.2",
  "org.scalikejdbc" %% "scalikejdbc-config"           % "3.0.2",
  "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.6.0-scalikejdbc-3.0"
)