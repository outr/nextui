package com.outr.nextui.desktop

import javafx.event.EventHandler

import com.outr.nextui.Button
import com.outr.nextui.event.ActionEvent

class JavaFXButton(val component: Button) extends javafx.scene.control.Button with JavaFXComponent {
  override def init(): Unit = {
    super.init()

    component.text.attach(setText)
    setOnAction(new EventHandler[javafx.event.ActionEvent] {
      override def handle(event: javafx.event.ActionEvent): Unit = component.action := new ActionEvent
    })
  }
}