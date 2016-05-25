package com.outr.nextui.desktop

import javafx.scene.layout.Pane

import com.outr.nextui.{Component, Container}

trait JavaFXContainer[C <: Component] extends JavaFXComponent {
  this: Container[C] =>

  lazy val peer: Pane = new Pane
}
