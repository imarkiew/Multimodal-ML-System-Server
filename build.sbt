name := "Multimodal-ML-System-Server"

version := "1.0"

scalaVersion := "2.11.12"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies += guice

//assemblyMergeStrategy in assembly := {
//  case x if x.startsWith("reference.conf") => MergeStrategy.concat
//  case PathList("META-INF", m) if m.equalsIgnoreCase("MANIFEST.MF") =>  MergeStrategy.discard
////  case x =>
////    val oldStrategy = (assemblyMergeStrategy in assembly).value
////    oldStrategy(x)
//
////  case PathList("reference.conf") => MergeStrategy.concat
//  case _ => MergeStrategy.first
//}

assemblyMergeStrategy in assembly := {
  case manifest if manifest.contains("MANIFEST.MF") =>
    // We don't need manifest files since sbt-assembly will create
    // one with the given settings
    MergeStrategy.discard
  case referenceOverrides if referenceOverrides.contains("reference-overrides.conf") =>
    // Keep the content for all reference-overrides.conf files
    MergeStrategy.concat
  case x =>
    // For all the other files, use the default sbt-assembly merge strategy
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

mainClass in assembly := Some("play.core.server.ProdServerStart")
fullClasspath in assembly += Attributed.blank(PlayKeys.playPackageAssets.value)

//excludeFilter in Compile := "myconfig.conf"

unmanagedResourceDirectories in Test +=  baseDirectory( _ /"target/web/public/test" ).value