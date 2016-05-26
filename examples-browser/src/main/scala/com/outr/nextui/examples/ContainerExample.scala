package com.outr.nextui.examples

import com.outr.nextui.browser.ScalaJS
import com.outr.nextui.model.Image
import com.outr.nextui.{Container, ImageView, UI}
import org.powerscala.Color

object ContainerExample extends UI with ScalaJS {
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
      image := Some(Image("https://puppydogweb.com/gallery/puppies/labradorretriever2.jpg"))
      width.pref := 250.0
    }
  }
}