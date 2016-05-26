package com.outr.nextui.examples

import com.outr.nextui.desktop.JavaFX
import com.outr.nextui.{Button, UI}

object HelloWorld extends UI with JavaFX {
  title := "Hello World"
  width.pref := 300.0
  height.pref := 250.0

  children += new Button {
    text := "Say 'Hello World'"
    center := HelloWorld.center
    middle := HelloWorld.middle

    action.attach { evt =>
      println("Hello World!")
    }
  }
}