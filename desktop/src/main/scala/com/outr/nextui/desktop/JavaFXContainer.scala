package com.outr.nextui.desktop

import com.outr.nextui.{Component, Container}

trait JavaFXContainer extends JavaFXComponent {
  override val impl: javafx.scene.layout.Pane = new javafx.scene.layout.Pane
}

object JavaFXContainer {
  def apply(container: Container): JavaFXContainer = new JavaFXContainer {
    override val component: Component = container
  }
}