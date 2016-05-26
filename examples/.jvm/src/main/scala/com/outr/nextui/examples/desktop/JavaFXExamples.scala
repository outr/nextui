package com.outr.nextui.examples.desktop

import java.util.prefs.Preferences

import com.outr.nextui.desktop.JavaFX
import com.outr.nextui.examples.Examples._

import scala.io.StdIn

object JavaFXExamples {
  def main(args: Array[String]): Unit = {
    val keyName = "nextui.javafx.examples.lastRun"
    val preferences = Preferences.userRoot()
    val previousName = preferences.get(keyName, examples.head.name)
    val previous = examples.find(_.name == previousName).map(examples.indexOf).getOrElse(0) + 1

    println("Choose an example to run:")
    examples.zipWithIndex.foreach {
      case (example, index) => println(s"\t${index + 1}.) ${example.name}")
    }
    println(s"Example number 1 - ${examples.size} [$previous]: ")
    val i = try {
      StdIn.readInt()
    } catch {
      case t: Throwable => previous
    }
    val ui = examples(i - 1)
    preferences.put(keyName, ui.name)
    println(s"Starting ${ui.name}...")
    JavaFX(ui)
  }
}