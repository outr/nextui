import sbt.Keys._
import sbt._

object NextUIBuild extends Build {
  import Dependencies._

  lazy val root = Project(id = "root", base = file(".")).settings(name := "nextui").aggregate(core, desktop, browser, examples)
  lazy val core = project("core").withDependencies(scribe.core, scribe.slf4j, metastack.rx, powerscala.core)

  // Platforms
  lazy val desktop = project("desktop").dependsOn(core)
  lazy val browser = project("browser").dependsOn(core)

  // Samples / Examples
  lazy val examples = project("examples").dependsOn(desktop)

  private def project(projectName: String) = Project(id = projectName, base = file(projectName)).settings(
    name := s"${Details.name}-$projectName",
    version := Details.version,
    organization := Details.organization,
    scalaVersion := Details.scalaVersion,
    sbtVersion := Details.sbtVersion,
    fork := true,
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

  implicit class EnhancedProject(project: Project) {
    def withDependencies(modules: ModuleID*) = project.settings(libraryDependencies ++= modules)
  }
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
    private val version = "0.1.6"

    val rx = "pl.metastack" %%  "metarx" % version
  }

  object powerscala {
    private val version = "2.0.2-SNAPSHOT"

    val core = "org.powerscala" %% "powerscala-core" % version
  }

  object scribe {
    private val group = "com.outr.scribe"
    private val version = "1.2.2"

    val core = group %% "scribe" % version
    val slf4j = group %% "scribe-slf4j" % version
  }
}
