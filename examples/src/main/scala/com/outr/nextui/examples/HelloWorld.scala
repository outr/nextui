package com.outr.nextui.examples

import com.outr.nextui.desktop.JavaFX
import com.outr.nextui.{Button, Scene, UI}

object HelloWorld extends UI with JavaFX {
  title := "Hello World"

  val scene = new Scene

  val btn = new Button {
    text := "Say 'Hello World'"
  }

  scene.children += btn
  currentScene := scene
}
