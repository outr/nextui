package com.outr.nextui.examples

import com.outr.nextui.browser.ScalaJS
import com.outr.nextui.{Button, UI}

object HelloWorld extends UI with ScalaJS {
  title := "Hello World"

  children += new Button {
    text := "Say 'Hello World'"
    center := HelloWorld.center
    middle := HelloWorld.middle

    action.attach { evt =>
      logger.info(s"Hello World!")
    }
  }
}