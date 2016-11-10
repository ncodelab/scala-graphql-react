val name = "scala-grapqhl-react"

scalaVersion := "2.11.8"

val commonSettings = Seq(
  organization := "com.ncodelab",
  version := "0.1.0",
  scalaVersion := "2.11.8",
  libraryDependencies ++= Seq(
    "ch.qos.logback" % "logback-classic" % "1.1.7",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
    "org.scalatest" %% "scalatest" % "3.0.0" % "test"
  )
)


val server = Project(s"$name-server", file("server"))
  .settings(commonSettings:_*)
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe" % "config" % "1.3.1",

      "org.sangria-graphql" %% "sangria" % "1.0.0-RC3",
      "org.sangria-graphql" %% "sangria-spray-json" % "0.3.1",
      "com.typesafe.akka" %% "akka-http-experimental" % "2.4.11",
      "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "2.4.11"
    )
  )


val client = Project(s"$name-client", file("client"))
  .enablePlugins(SbtWeb)
  .settings(commonSettings:_*)


val root = Project(name, file(".")).aggregate(server, client)
