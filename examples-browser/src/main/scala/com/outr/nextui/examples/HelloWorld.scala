package com.outr.nextui.examples

import com.outr.nextui.browser.ScalaJS
import com.outr.nextui.{Button, UI}

object HelloWorld extends UI with ScalaJS {
  title := "Hello World"
  width.pref := 300.0
  height.pref := 250.0

  children += new Button {
    text := "Say 'Hello World'"
//    center := HelloWorld.center
//    middle := HelloWorld.middle
    x := HelloWorld.width.actual / 2.0
    y := HelloWorld.height.actual / 2.0

    action.attach { evt =>
      println("Hello World!")
    }
  }
}