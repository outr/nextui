import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt.Keys._
import sbt._

object NextUIBuild extends Build {
  import Dependencies._

  lazy val root = project.in(file("."))
    .aggregate(coreJs, coreJvm, desktop, browser, examplesJs, examplesJvm)
    .settings(sharedSettings(): _*)
    .settings(publishArtifact := false)
  lazy val core = crossProject.crossType(CrossType.Pure).in(file("core"))
    .settings(withCompatUnmanagedSources(jsJvmCrossProject = true, include_210Dir = false, includeTestSrcs = true): _*)
    .settings(sharedSettings(): _*)
    .settings(
      autoAPIMappings := true,
      apiMappings += (scalaInstance.value.libraryJar -> url(s"http://www.scala-lang.org/api/${scalaVersion.value}/"))
    )
    .jsSettings(
      libraryDependencies ++= Seq(
        powerscala.group %%% powerscala.core % powerscala.version,
        scalaJs.group %%% scalaJs.dom % scalaJs.version,
        metastack.group %%% metastack.rx % metastack.version,
        scribe.group %%% scribe.core % scribe.version,
        scalaTest.group %%% scalaTest.core % scalaTest.version % "test"
      ),
      scalaJSStage in Global := FastOptStage
    )
    .jvmSettings(
      javaOptions += "-verbose:gc",
      libraryDependencies ++= Seq(
        powerscala.group %% powerscala.core % powerscala.version,
        metastack.group %% metastack.rx % metastack.version,
        scribe.group %% scribe.core % scribe.version,
        scalaTest.group %% scalaTest.core % scalaTest.version % "test"
      )
    )
  lazy val coreJs = core.js
  lazy val coreJvm = core.jvm

  // Platforms
  lazy val desktop = project.in(file("desktop"))
    .settings(sharedSettings(Some("desktop")))
    .dependsOn(coreJvm)
  lazy val browser = project.in(file("browser"))
    .settings(sharedSettings(Some("browser")))
    .enablePlugins(ScalaJSPlugin)
    .dependsOn(coreJs)

  // Samples / Examples
  lazy val examples = crossProject.crossType(CrossType.Pure).in(file("examples"))
    .dependsOn(core)
    .settings(withCompatUnmanagedSources(jsJvmCrossProject = true, include_210Dir = false, includeTestSrcs = true): _*)
    .settings(sharedSettings(Some("examples")): _*)
    .settings(
      autoAPIMappings := true,
      apiMappings += (scalaInstance.value.libraryJar -> url(s"http://www.scala-lang.org/api/${scalaVersion.value}/"))
    )
  lazy val examplesJs = examples.js.dependsOn(browser).enablePlugins(ScalaJSPlugin)
  lazy val examplesJvm = examples.jvm.dependsOn(desktop).settings(fork := true)

  def sharedSettings(projectName: Option[String] = None) = Seq(
    name := s"${Details.name}${projectName.map(pn => s"-$pn").getOrElse("")}",
    version := Details.version,
    organization := Details.organization,
    scalaVersion := Details.scalaVersion,
    sbtVersion := Details.sbtVersion,
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),
    resolvers ++= Seq(
      Resolver.sonatypeRepo("snapshots"),
      Resolver.sonatypeRepo("releases"),
      Resolver.typesafeRepo("releases")
    ),
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

  /**
    * Helper function to add unmanaged source compat directories for different scala versions
    */
  private def withCompatUnmanagedSources(jsJvmCrossProject: Boolean, include_210Dir: Boolean, includeTestSrcs: Boolean): Seq[Setting[_]] = {
    def compatDirs(projectbase: File, scalaVersion: String, isMain: Boolean) = {
      val base = if (jsJvmCrossProject ) projectbase / ".." else projectbase
      CrossVersion.partialVersion(scalaVersion) match {
        case Some((2, scalaMajor)) if scalaMajor >= 11 => Seq(base / "compat" / "src" / (if (isMain) "main" else "test") / "scala-2.11").map(_.getCanonicalFile)
        case Some((2, scalaMajor)) if scalaMajor == 10 && include_210Dir => Seq(base / "compat" / "src" / (if (isMain) "main" else "test") / "scala-2.10").map(_.getCanonicalFile)
        case _ => Nil
      }
    }
    val unmanagedMainDirsSetting = Seq(
      unmanagedSourceDirectories in Compile ++= {
        compatDirs(projectbase = baseDirectory.value, scalaVersion = scalaVersion.value, isMain = true)
      }
    )
    if (includeTestSrcs) {
      unmanagedMainDirsSetting ++ {
        unmanagedSourceDirectories in Test ++= {
          compatDirs(projectbase = baseDirectory.value, scalaVersion = scalaVersion.value, isMain = false)
        }
      }
    } else {
      unmanagedMainDirsSetting
    }
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
    val group = "pl.metastack"
    val version = "0.1.8-SNAPSHOT"

    val rx = "metarx"
  }
  object powerscala {
    val group = "org.powerscala"
    val version = "2.0.2-SNAPSHOT"

    val core = "powerscala-core"
  }
  object scalaJs {
    val group = "org.scala-js"
    val version = "0.9.0"

    val dom = "scalajs-dom"
  }
  object scribe {
    val group = "com.outr.scribe"
    val version = "1.2.3"

    val core = "scribe"
    val slf4j = "scribe-slf4j"
  }
  object scalaTest {
    val group = "org.scalatest"
    val version = "3.0.0-M16-SNAP4"

    val core = "scalatest"
  }
}