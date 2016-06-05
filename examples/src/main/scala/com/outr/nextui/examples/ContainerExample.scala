package com.outr.nextui.examples

import com.outr.nextui.model.Resource
import com.outr.nextui.{Container, ImageView, UI}
import org.powerscala.Color

object ContainerExample extends UI {
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
      src := Resource("tucker.jpg")
      width.pref := 250.0
    }
  }
}