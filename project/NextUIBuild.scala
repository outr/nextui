import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt.Keys._
import sbt._

object NextUIBuild extends Build {
  import Dependencies._

  def sharedSettings(projectName: Option[String] = None) = Seq(
    name := s"${Details.name}${projectName.map(pn => s"-$pn").getOrElse("")}",
    version := Details.version,
    organization := Details.organization,
    scalaVersion := Details.scalaVersion,
    sbtVersion := Details.sbtVersion,
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),
    javaOptions += "-verbose:gc",
    resolvers ++= Seq(
      "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
      "Typesafe Repository" at "https://repo.typesafe.com/typesafe/releases/"
    ),
    publishTo <<= version {
      (v: String) =>
        val nexus = "https://oss.sonatype.org/"
        if (v.trim.endsWith("SNAPSHOT"))
          Some("snapshots" at nexus + "content/repositories/snapshots")
        else
          Some("releases" at nexus + "service/local/staging/deploy/maven2")
    },
    addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full),
    libraryDependencies <+= scalaVersion("org.scala-lang" % "scala-reflect" % _),
    publishArtifact in Test := false,
    pomExtra := <url>${Details.url}</url>
      <licenses>
        <license>
          <name>${Details.licenseType}</name>
          <url>${Details.licenseURL}</url>
          <distribution>repo</distribution>
        </license>
      </licenses>
      <scm>
        <developerConnection>scm:${Details.repoURL}</developerConnection>
        <connection>scm:${Details.repoURL}</connection>
        <url>${Details.projectURL}</url>
      </scm>
      <developers>
        <developer>
          <id>${Details.developerId}</id>
          <name>${Details.developerName}</name>
          <url>${Details.developerURL}</url>
        </developer>
      </developers>
  )

  lazy val root = project.in(file("."))
    .aggregate(js, jvm, desktop, browser, examples)
    .settings(sharedSettings(): _*)
    .settings(publishArtifact := false)
  lazy val core = crossProject.in(file("."))
    .settings(sharedSettings(): _*)
    .settings(
      autoAPIMappings := true,
      apiMappings += (scalaInstance.value.libraryJar -> url(s"http://www.scala-lang.org/api/${scalaVersion.value}/"))
    )
    .jsSettings(
      libraryDependencies ++= Seq(
        metastack.group %%% metastack.rx % metastack.version,
        scribe.group %%% scribe.core % scribe.version,
        scalaTest.group %%% scalaTest.core % scalaTest.version % "test"
      ),
      scalaJSStage in Global := FastOptStage
    )
    .jvmSettings(
      libraryDependencies ++= Seq(
        metastack.group %% metastack.rx % metastack.version,
        scribe.group %% scribe.core % scribe.version,
        scalaTest.group %% scalaTest.core % scalaTest.version % "test"
      )
    )
  lazy val js = core.js
  lazy val jvm = core.jvm

  // Platforms
  lazy val desktop = project.in(file("desktop"))
    .settings(sharedSettings(Some("desktop")))
    .dependsOn(jvm)
  lazy val browser = project.in(file("browser"))
    .settings(sharedSettings(Some("browser")))
    .dependsOn(js)

  // Samples / Examples
  lazy val examples = project.in(file("examples"))
    .settings(sharedSettings(Some("examples")))
    .settings(fork := true)
    .dependsOn(desktop)
}

object Details {
  val organization = "com.outr.nextui"
  val name = "nextui"
  val version = "1.0.0-SNAPSHOT"
  val url = "http://outr.com"
  val licenseType = "Apache 2.0"
  val licenseURL = "http://www.apache.org/licenses/LICENSE-2.0"
  val projectURL = "https://gitlab.com/outr/nextui"
  val repoURL = "https://gitlab.com/outr/nextui.git"
  val developerId = "darkfrog"
  val developerName = "Matt Hicks"
  val developerURL = "http://matthicks.com"

  val sbtVersion = "0.13.11"
  val scalaVersion = "2.11.8"
}

object Dependencies {
  object metastack {
    val group = "pl.metastack"
    val version = "0.1.8-SNAPSHOT"

    val rx = "metarx"
  }

  object scribe {
    val group = "com.outr.scribe"
    val version = "1.2.2"

    val core = "scribe"
    val slf4j = "scribe-slf4j"
  }

  object scalaTest {
    val group = "org.scalatest"
    val version = "3.0.0-M16-SNAP4"

    val core = "scalatest"
  }
}
