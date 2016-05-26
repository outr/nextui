package com.outr.nextui.desktop

trait JavaFXContainer extends JavaFXComponent {
  override val impl: javafx.scene.layout.Pane = new javafx.scene.layout.Pane
}