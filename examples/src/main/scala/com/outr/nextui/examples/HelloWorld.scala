package com.outr.nextui.examples

import com.outr.nextui.Button
import com.outr.nextui.desktop.JavaFX

object HelloWorld extends JavaFX {
  title := "Hello World"
  width := 300.0
  height := 250.0

  children += new Button {
    text := "Say 'Hello World'"

    action.attach { evt =>
      println("Hello World!")
    }
  }
}