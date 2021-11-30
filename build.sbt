name := "spork"

version := "0.1"

scalaVersion := "2.13.7"

val akkaVersion = "2.6.17"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
  "com.typesafe.akka" %% "akka-cluster-typed" % akkaVersion,
  "com.github.pureconfig" %% "pureconfig" % "0.17.1",
  "org.typelevel" %% "cats-core" % "2.3.0"
)