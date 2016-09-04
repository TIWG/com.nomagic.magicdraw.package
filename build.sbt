import sbt.Keys._
import sbt._

enablePlugins(AetherPlugin)

scalaVersion := "2.11.8"

updateOptions := updateOptions.value.withCachedResolution(true)

resolvers += Resolver.bintrayRepo("jpl-imce", "gov.nasa.jpl.imce")

resolvers += Resolver.bintrayRepo("tiwg", "org.omg.tiwg")

PgpKeys.useGpg := true

PgpKeys.useGpgAgent := true

pgpSecretRing := file("local.secring.gpg")

pgpPublicRing := file("local.pubring.gpg")

// publish to bintray.com via: `sbt publish`
publishTo := Some(
  "TIWG" at
    s"https://api.bintray.com/content/tiwg/org.omg.tiwg.vendor.nomagic/com.nomagic.magicdraw.package/${version.value}")

val noSourcesSettings: Seq[Setting[_]] = Seq(

  publishArtifact in Compile := false,
  sources in Compile := Seq.empty,

  publishArtifact in Test := false,
  sources in Test := Seq.empty,

  managedSources := Seq.empty,

  organization := "org.omg.tiwg.vendor.nomagic",

  version := "18.0-sp6",

  // disable automatic dependency on the Scala library
  autoScalaLibrary := false,

  // disable using the Scala version in output paths and artifacts
  crossPaths := false,

  publishMavenStyle := true,

  // do not include all repositories in the POM
  pomAllRepositories := false,

  // make sure no repositories show up in the POM file
  pomIncludeRepository := { _ => false }
)

ivyLoggingLevel := UpdateLogging.Full

logLevel in Compile := Level.Debug

persistLogLevel := Level.Debug

def zipArtifacts(parts: Seq[File]): Seq[Setting[_]] = {
  parts.flatMap { part =>
    val components = part.baseAndExt._1.split('.')
    val (art,index) = (components(0), components(1))
    println(s"part: $index")
    println(s"art: $art")
    val a = Def.setting[Artifact] {
      Artifact(art, "zip", "zip", Some(index), Seq(), None, Map())
    }
    val f = Def.task[File] {
      part
    }
    addArtifact(a, f)
  }
}

lazy val core = Project("com_nomagic_magicdraw_package_upload", file("."))
  .settings(noSourcesSettings)
  .settings(
    name := "com.nomagic.magicdraw.package",
    moduleName := name.value,
    organization := "org.omg.tiwg.vendor.nomagic")
  .settings(zipArtifacts( ((file(".") / "downloads" / "parts") * "*.part*.zip").get ) : _*)


