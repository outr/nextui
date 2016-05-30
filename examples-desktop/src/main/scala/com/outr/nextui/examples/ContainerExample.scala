package com.outr.nextui.examples

import com.outr.nextui.desktop.JavaFX
import com.outr.nextui.{Container, ImageView, UI}
import org.powerscala.Color

object ContainerExample extends UI with JavaFX {
  title := "Container Example"
  width := 510.0
  height := 410.0

  children += new Container {
    x := 100.0
    y := 100.0
    width := 400.0
    height := 300.0
    background := Color.Aquamarine

    children += new ImageView {
      src := classLoader("tucker.jpg")
      width.pref := 250.0
    }
  }
}