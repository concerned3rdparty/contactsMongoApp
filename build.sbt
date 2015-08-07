name := "contactMongo"

version := "1.0"

libraryDependencies ++= Seq(
  "org.mongodb" %% "casbah" % "2.8.2",
  "org.specs2" %% "specs2" % "2.3.12" % Test,
  "org.mockito" % "mockito-core" % "1.9.5" % Test
)

//libraryDependencies += "org.scala-lang" % "scala-xml" % "2.11.0-M4"
