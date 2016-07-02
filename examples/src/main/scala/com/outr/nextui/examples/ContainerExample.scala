package com.outr.nextui.examples

import com.outr.nextui.model.Resource
import com.outr.nextui.{Container, ImageView, UI}
import org.powerscala.Color

object ContainerExample extends UI {
  title := "Container Example"
  size.width := 510.0
  size.height := 410.0

  children += new Container {
    position.x := 100.0
    position.y := 100.0
    size.width := 400.0
    size.height := 300.0
    background := Color.Aquamarine

    children += new ImageView {
      src := Resource("tucker.jpg")
      size.width.pref := 250.0
    }
  }
}