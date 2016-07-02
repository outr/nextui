package com.outr.nextui.examples

import com.outr.nextui.{Button, UI}

object HelloWorld extends UI {
  title := "Hello World"
  size.width.pref := 300.0
  size.height.pref := 250.0

  children += new Button {
    text := "Say 'Hello World'"
    position.center := ui.position.center
    position.middle := ui.position.middle

    action.attach { evt =>
      logger.info("Hello World!")
    }
  }
}