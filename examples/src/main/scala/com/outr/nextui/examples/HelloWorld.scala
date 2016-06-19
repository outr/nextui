package com.outr.nextui.examples

import com.outr.nextui.{Label, UI}

object HelloWorld extends UI {
  title := "Hello World Example"
  width.pref := 300.0
  height.pref := 250.0

  children += new Label {
    text := "Hello World"
    center := ui.center
    middle := ui.middle
  }
}