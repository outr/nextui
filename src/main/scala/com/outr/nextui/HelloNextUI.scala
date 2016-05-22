package com.outr.nextui

object HelloNextUI extends UI {
  val btn = scene += new Button {
    text := "Say 'Hello World'"
  }
}
