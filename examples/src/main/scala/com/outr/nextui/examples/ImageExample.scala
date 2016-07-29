package com.outr.nextui.examples

import com.outr.nextui.model.{Image, Resource}
import com.outr.nextui.{Button, ImageView, UI}
import pl.metastack.metarx._

object ImageExample extends UI {
  title := "Image Example"
  size.width := 1024
  size.height := 768

  lazy val img1 = Image(Resource("tucker.jpg"))
  lazy val img2 = Image("https://img.buzzfeed.com/buzzfeed-static/static/2014-07/28/10/enhanced/webdr11/enhanced-12094-1406557546-35.jpg")
  val imageView = new ImageView {
    image := img1

    // Keep the image centered
    position.center := ui.position.center
    position.middle := ui.position.middle

    // Default the window size to be the size of the image when it loads
    ui.size.width.pref := size.width.actual.map(d => Option(d))
    ui.size.height.pref := size.height.actual.map(d => Option(d))
  }
  val button = new Button {
    text := "Toggle Image"

    position.right := ui.position.right - 50.0
    position.bottom := ui.position.bottom - 50.0

    action.attach { evt =>
      if (imageView.image.get.contains(img1)) {
        imageView.image := img2
      } else {
        imageView.image := img1
      }
    }
  }

  children += imageView
  children += button
}