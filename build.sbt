ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "Cinema-challenge",
    libraryDependencies ++= Seq(
      "org.scalatestplus" %% "mockito-3-4" % "3.2.10.0" % Test,
      "org.scalatest" %% "scalatest" % "3.2.15" % Test,
      "org.typelevel" %% "cats-kernel" % "2.9.0",
      "org.typelevel" %% "cats-core" % "2.9.0",
      "io.jvm.uuid" %% "scala-uuid" % "0.3.1"
    )
  )
