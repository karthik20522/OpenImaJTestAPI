organization := "com.OpenImaJTestAPI"

name := "OpenImaJTestAPI"

version := "0.1"

scalaVersion := "2.10.3"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers ++= Seq(
  "spray repo" at "http://repo.spray.io/",
  "openImaJ repo" at "http://maven.openimaj.org"
)

libraryDependencies ++= {
  val akkaV = "2.1.4"
  val sprayV = "1.1.0"
  Seq(
    "io.spray" % "spray-can" % sprayV,
    "io.spray" % "spray-routing" % sprayV,
    "io.spray" % "spray-testkit" % sprayV,
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-testkit" % akkaV,
    "org.specs2" %% "specs2" % "2.2.3" % "test",
    "org.json4s" %% "json4s-native" % "3.2.6",
    "com.sksamuel.scrimage" % "scrimage-core_2.10" % "1.3.14",
    "com.sksamuel.scrimage" % "scrimage-filters_2.10" % "1.3.14",
    "net.databinder.dispatch" %% "dispatch-core" % "0.11.0",
    "com.github.nscala-time" %% "nscala-time" % "0.8.0",
    "com.drewnoakes" % "metadata-extractor" % "2.6.2",
    "org.openimaj" % "image-processing" % "1.2",
    "org.openimaj" % "faces" % "1.2",
    "org.openimaj.tools" % "core-tool" % "1.2",
    "com.google.guava" % "guava" % "16.0.1",
    "com.google.code.findbugs" % "jsr305" % "2.0.3",
  	"com.typesafe" %% "scalalogging-slf4j" % "1.0.1",
 	"com.github.seratch" %% "awscala" % "0.1.4"
  )
}

seq(Revolver.settings: _*)