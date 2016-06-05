package com.outr.nextui.examples

object HelloWorld extends UI {
  title := "Hello World"
  width.pref := 300.0
  height.pref := 250.0

  children += new Button {
    text := "Say 'Hello World'"
    center := ui.center
    middle := ui.middle

    action.attach { evt =>
      logger.info("Hello World!")
    }
  }

  def main(args: Array[String]): Unit = {
    JavaFX(this)
  }
}