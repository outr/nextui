package com.outr.nextui.desktop

import javafx.application.Application
import javafx.stage.Stage

import com.outr.nextui.{Button, Component, Scene, UI}

import scala.collection.mutable.ListBuffer

trait JavaFX {
  this: UI =>

  def main(args: Array[String]): Unit = {
    println("Starting JavaFX...")
    JavaFXApplication.prepare(this)
    Application.launch(classOf[JavaFXApplication])
  }

  def initialize(primaryStage: Stage, application: JavaFXApplication): Unit = {
    primaryStage.setTitle(title.get)
    iterator.foreach {
      case b: Button => println(s"Button: $b")
      case s: Scene => println(s"Scene: $s")
      case c => throw new RuntimeException(s"Unsupported component: ${c.getClass.getName}.")
    }
    primaryStage.show()
  }
}

class JavaFXApplication extends Application {
  override def start(primaryStage: Stage): Unit = {
    val ui = JavaFXApplication.use()
    ui.initialize(primaryStage, this)
  }
}

object JavaFXApplication {
  private var instance: Option[JavaFX] = None

  def use(): JavaFX = synchronized {
    instance match {
      case Some(ui) => ui
      case None => throw new RuntimeException("No UI defined for JavaFX.")
    }
  }

  def prepare(ui: JavaFX): Unit = synchronized {
    instance match {
      case Some(existing) => throw new RuntimeException(s"Cannot define multiple UIs before initialization. Already defined: $existing, Trying to define: $ui.")
      case None => instance = Some(ui)
    }
  }
}