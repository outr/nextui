package com.outr.nextui.desktop

import javafx.event.EventHandler

import com.outr.nextui.Button
import com.outr.nextui.event.ActionEvent

trait JavaFXButton extends JavaFXComponent {
  this: Button =>

  lazy val peer: javafx.scene.control.Button = new javafx.scene.control.Button

  override def init(): Unit = {
    super.init()

    text.attach { s =>
      peer.setText(s)
    }
    peer.setOnAction(new EventHandler[javafx.event.ActionEvent] {
      override def handle(event: javafx.event.ActionEvent): Unit = action := new ActionEvent
    })
  }
}
