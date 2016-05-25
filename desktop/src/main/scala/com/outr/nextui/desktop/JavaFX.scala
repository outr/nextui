package com.outr.nextui.desktop

import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.layout.StackPane
import javafx.stage.Stage

import com.outr.nextui.event.ActionEvent
import com.outr.nextui.{Button, Component, Screen, UI}
import pl.metastack.metarx.{ReadStateChannel, Sub}

trait JavaFX {
  this: UI =>

  val width: Sub[Double] = Sub[Double](800.0)
  val height: Sub[Double] = Sub[Double](600.0)

  def main(args: Array[String]): Unit = {
    println("Starting JavaFX...")
    JavaFXApplication.prepare(this)
    Application.launch(classOf[JavaFXApplication])
  }

  def initialize(primaryStage: Stage, application: JavaFXApplication): Unit = {
    primaryStage.setTitle(title.get)
    iterator.foreach {
      case b: Button => {
        val peer = new javafx.scene.control.Button
        manage(b.text, (title: String) => peer.setText(title))
        manage(b.parent, (parentOption: Option[Component]) => {
          val parent = parentOption.getOrElse(throw new RuntimeException(s"No parent assigned to this component: $b."))
          val container = parent.peer.getOrElse(throw new RuntimeException(s"No peer defined for $parent."))
          container.asInstanceOf[javafx.scene.Scene].getRoot.asInstanceOf[javafx.scene.layout.Pane].getChildren.add(peer)
        })
        peer.setOnAction(new EventHandler[javafx.event.ActionEvent] {
          override def handle(event: javafx.event.ActionEvent): Unit = b.events.action := new ActionEvent
        })
        b.peer = Some(peer)
      }
      case s: Screen => {
        val peer = new javafx.scene.Scene(new StackPane)
        s.peer = Some(peer)
        primaryStage.setScene(peer)
        primaryStage.setWidth(width.get)
        primaryStage.setHeight(height.get)
      }
      case c => throw new RuntimeException(s"Unsupported component: ${c.getClass.getName}.")
    }
    primaryStage.show()
  }

  def manage[T](channel: ReadStateChannel[T], changed: T => Unit): Unit = {
    channel.attach(changed)
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
      case Some(ui) => {
        instance = None
        ui
      }
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