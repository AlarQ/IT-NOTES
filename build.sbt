name := """ITNotes"""
organization := "com.alarq"

version := "1.0-SNAPSHOT"

PlayKeys.devSettings := Seq("play.akka.dev-mode.akka.http.parsing.max-uri-length" -> "4096")


lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.6"
javaOptions += "-Dakka.http.parsing.max-header-uri-length=16k"

val elastic4sVersion = "7.14.0"


libraryDependencies ++= Seq(
  guice,
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
  "com.softwaremill.quicklens" %% "quicklens" % "1.7.4",
  "org.sangria-graphql" %% "sangria" % "2.1.3",
  "org.sangria-graphql" %% "sangria-play-json" % "2.0.2",
  "org.scalatest" %% "scalatest" % "3.2.9" % Test,
  "com.sksamuel.elastic4s" %% "elastic4s-core" % elastic4sVersion,
  "com.sksamuel.elastic4s" %% "elastic4s-client-esjava" % elastic4sVersion,
  "com.sksamuel.elastic4s" %% "elastic4s-json-circe" % elastic4sVersion,
  "com.sksamuel.elastic4s" %% "elastic4s-testkit" % elastic4sVersion % "test",
  "com.softwaremill.common" %% "id-generator" % "1.3.1",
  "com.chuusai" %% "shapeless" % "2.3.7"
)

val circeVersion = "0.14.1"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)