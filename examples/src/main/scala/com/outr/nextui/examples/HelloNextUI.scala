package com.outr.nextui.examples

import com.outr.nextui.desktop.JavaFXSupport
import com.outr.nextui.{Button, Scene, UI}

object HelloNextUI extends UI with JavaFXSupport {
  title := "Hello World"

  val scene = new Scene

  val btn = new Button {
    text := "Say 'Hello World'"
  }

  scene += btn
  currentScene := scene
}
