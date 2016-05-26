package com.outr.nextui.desktop

import javafx.event.EventHandler

import com.outr.nextui.Button
import com.outr.nextui.event.ActionEvent

class JavaFXButton(val component: Button) extends JavaFXComponent {
  override val impl: javafx.scene.control.Button = new javafx.scene.control.Button

  override def init(): Unit = {
    super.init()

    component.text.attach(impl.setText)
    impl.setOnAction(new EventHandler[javafx.event.ActionEvent] {
      override def handle(event: javafx.event.ActionEvent): Unit = component.action := new ActionEvent
    })
  }
}