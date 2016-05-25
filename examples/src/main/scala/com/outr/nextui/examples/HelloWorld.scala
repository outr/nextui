package com.outr.nextui.examples

import com.outr.nextui.desktop.JavaFX
import com.outr.nextui.{Button, Screen, UI}

object HelloWorld extends UI with JavaFX {
  title := "Hello World"
  width := 300.0
  height := 250.0

  val screen = new Screen

  val btn = new Button {
    text := "Say 'Hello World'"

    events.action.attach { evt =>
      println("Hello World!")
    }
  }

  screen.children += btn
  currentScreen := screen
}
