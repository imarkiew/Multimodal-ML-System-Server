name := "Multimodal-ML-System-Server"

version := "1.0"

scalaVersion := "2.11.12"

lazy val root = (project in file(".")).enablePlugins(PlayScala)
scalaSource in ThisScope := baseDirectory.value
mainClass in assembly := Some("play.core.server.ProdServerStart")
fullClasspath in assembly += Attributed.blank(PlayKeys.playPackageAssets.value)

libraryDependencies ++= Seq(
  guice,
  ws,
  specs2 % Test,
  "com.typesafe.play" %% "play-json" % "2.7.4",
  "com.typesafe.play" %% "play-slick" % "4.0.2",
  "com.typesafe.play" %% "play-slick-evolutions" % "4.0.2",
  "com.typesafe" % "config" % "1.4.0",
  "org.mindrot" % "jbcrypt" % "0.4",
  "mysql" % "mysql-connector-java" % "8.0.17",
  "org.mindrot" % "jbcrypt" % "0.4",
  "com.iheart" %% "ficus" % "1.4.7",
  "com.typesafe.scala-logging" % "scala-logging_2.11" % "3.9.0"
)

assemblyMergeStrategy in assembly := {
  case manifest if manifest.contains("MANIFEST.MF") =>
    // We don't need manifest files since sbt-assembly will create
    // one with the given settings
    MergeStrategy.discard
  case PathList("javax", _ @ _*) =>
    //  pick the first of the matching files in classpath order
    MergeStrategy.first
  case referenceOverrides if referenceOverrides.contains("reference-overrides.conf") =>
    // Keep the content for all reference-overrides.conf files
    MergeStrategy.concat
  case x =>
    // For all the other files, use the default sbt-assembly merge strategy
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}
