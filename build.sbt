import com.typesafe.sbt.packager.docker.DockerChmodType
import com.typesafe.sbt.packager.docker.DockerPermissionStrategy

name := """ITNotes"""
organization := "com.alarq"

version := "1.0-SNAPSHOT"

// ------------------------- VERSIONS -------------------------
scalaVersion := "2.13.6"
val scalaTestVersion = "3.2.9"
val scalaTestPlusVersion = "5.1.0"

val sangriaVersion = "2.1.4"
val sangriaPlayVersion = "2.0.2"

val elastic4sVersion = "7.15.1"

val circeVersion = "0.14.1"

val quickLensVersion = "1.7.5"
val idGeneratorVersion = "1.3.1"
val shapelessVersion = "2.3.7"
// ------------------------- DEPENDENCIES ---------------------
val testing = Seq(
  "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
  "org.scalatestplus.play" %% "scalatestplus-play" % scalaTestPlusVersion % Test
)

val sangria = Seq(
  "org.sangria-graphql" %% "sangria" % sangriaVersion,
  "org.sangria-graphql" %% "sangria-play-json" % sangriaPlayVersion
)

val circe = Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

val elastic$s = Seq(
  "com.sksamuel.elastic4s" %% "elastic4s-core" % elastic4sVersion,
  "com.sksamuel.elastic4s" %% "elastic4s-client-esjava" % elastic4sVersion,
  "com.sksamuel.elastic4s" %% "elastic4s-json-circe" % elastic4sVersion,
  "com.sksamuel.elastic4s" %% "elastic4s-testkit" % elastic4sVersion % "test",
)

val others = Seq(
  "com.softwaremill.quicklens" %% "quicklens" % quickLensVersion,
  "com.softwaremill.common" %% "id-generator" % idGeneratorVersion,
  "com.chuusai" %% "shapeless" % shapelessVersion
)

libraryDependencies ++= Seq(guice) ++ testing ++ sangria ++ circe ++ elastic$s ++ others

// ------------------------- SETTINGS -------------------------
PlayKeys.devSettings := Seq("play.akka.dev-mode.akka.http.parsing.max-uri-length" -> "4096")

lazy val root = (project in file(".")).enablePlugins(PlayScala)
javaOptions += "-Dakka.http.parsing.max-header-uri-length=16k"

herokuAppName in Compile := "young-anchorage-54283"

dockerChmodType := DockerChmodType.UserGroupWriteExecute
dockerPermissionStrategy := DockerPermissionStrategy.CopyChown
enablePlugins(DockerPlugin)

