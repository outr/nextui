package com.outr.nextui.examples

import com.outr.nextui.desktop.{JavaFX, JavaFXButton}
import com.outr.nextui.{Button, UI}

object HelloWorld extends UI with JavaFX {
  title := "Hello World"
  width := 300.0
  height := 250.0

  children += new Button with JavaFXButton {
    text := "Say 'Hello World'"

    action.attach { evt =>
      println("Hello World!")
    }
  }
}