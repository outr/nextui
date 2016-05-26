package com.outr.nextui.examples

import com.outr.nextui.desktop.JavaFX
import com.outr.nextui.{Button, UI}

object HelloWorld extends UI with JavaFX {
  title := "Hello World"
  width.pref := 300.0
  height.pref := 250.0
  middle.attach { d =>
    println(s"Window middle: $d, Height: ${height.actual.get}, Y: ${y.get}")
  }

  children += new Button {
    text := "Say 'Hello World'"
    width.actual.attach { d =>
      println(s"Width: $d")
    }
    height.actual.attach { d =>
      println(s"Height: $d")
    }
    middle.attach { d =>
      println(s"Button middle: $d, Height: ${height.actual.get}, Y: ${y.get}")
    }

    action.attach { evt =>
      println("Hello World!")
      center := HelloWorld.center
      y := 112.0
    }
  }
}