package com.outr.nextui.desktop

import javafx.scene.text.{Font, FontPosture, FontWeight}

import com.outr.nextui.Label

class JavaFXLabel(val component: Label) extends JavaFXComponent {
  override val impl: javafx.scene.control.Label = new javafx.scene.control.Label

  override def initialize(): Unit = {
    super.initialize()

    component.text.attach(impl.setText)

    def updateFont(): Unit = {
      val f = component.font
      val weight = FontWeight.findByName(f.weight.get.name)
      val posture = FontPosture.findByName(f.style.get.name)
      impl.setFont(Font.font(f.family.get, weight, posture, f.size.get))
    }
    component.font.family.attach(s => updateFont())
    component.font.weight.attach(s => updateFont())
    component.font.style.attach(s => updateFont())
    component.font.size.attach(s => updateFont())
    component.color.attach(c => impl.setTextFill(new javafx.scene.paint.Color(c.red, c.green, c.blue, c.alpha)))
  }
}