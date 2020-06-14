name := "DtLab"
organization := "org.somind"
javacOptions ++= Seq("-source", "1.11", "-target", "1.11") 
scalacOptions ++= Seq(
  "-target:jvm-1.8"
)
fork := true
javaOptions in test ++= Seq(
  "-Xms128M", "-Xmx256M",
  "-XX:+CMSClassUnloadingEnabled"
)

version := "0.1.0"

parallelExecution in test := false

crossScalaVersions := List("2.13.2")

inThisBuild(List(
  organization := "org.somind",
  homepage := Some(url("https://github.com/navicore/dt-lab-scala-alligator")),
  licenses := List("MIT" -> url("https://github.com/navicore/dt-lab-scala-alligator/blob/master/LICENSE")),
  developers := List(
    Developer(
      "navicore",
      "Ed Sweeney",
      "ed@onextent.com",
      url("https://navicore.tech")
    )
  )
))

libraryDependencies ++=
  Seq(
    "org.scalatest" %% "scalatest" % "3.1.2" % "test"
  )

assemblyJarName in assembly := s"${name.value}-${version.value}.jar"

assemblyMergeStrategy in assembly := {
  case PathList("reference.conf") => MergeStrategy.concat
  case x if x.endsWith("io.netty.versions.properties") => MergeStrategy.first
  case PathList("META-INF", _ @ _*) => MergeStrategy.discard
  case _ => MergeStrategy.first
}

