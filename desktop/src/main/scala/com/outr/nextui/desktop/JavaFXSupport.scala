package com.outr.nextui.desktop

import javafx.application.Application
import javafx.stage.Stage

import com.outr.nextui.{Button, Component, Scene, UISupport}

import scala.collection.mutable.ListBuffer

trait JavaFXSupport extends UISupport {
  private val backlog = ListBuffer.empty[Component]
  private var initialized = false

  override def init(component: Component): Unit = if (initialized) {
    component match {
      case button: Button => {
        println(s"Button: $button")
      }
      case scene: Scene => {
        println(s"Scene: $scene")
      }
      case _ => throw new RuntimeException(s"Not supported ${component.getClass.getName}")
    }
  } else {
    backlog += component
  }

  def initialize(): Unit = {
    initialized = true
    backlog.foreach(init)
    backlog.clear()
  }

  def main(args: Array[String]): Unit = {
    println("Starting JavaFX...")
    JavaFXApplication.instance = Some(this)
    Application.launch(classOf[JavaFXApplication])
  }
}

class JavaFXApplication extends Application {
  override def start(primaryStage: Stage): Unit = JavaFXApplication.instance match {
    case Some(app) => {
      println(s"Starting with $app")
      app.initialize()
      primaryStage.setTitle(app.title.get)
      primaryStage.show()
    }
    case None => throw new RuntimeException(s"No JavaFXSupport instance defined!")
  }
}

object JavaFXApplication {
  var instance: Option[JavaFXSupport] = None
}