package com.outr.nextui.examples

import com.outr.nextui.browser.ScalaJS
import com.outr.nextui.model.Image
import com.outr.nextui.{Button, ImageView, UI}
import pl.metastack.metarx._

object ImageExample extends UI with ScalaJS {
  title := "Image Example"

  lazy val img1 = Image("https://puppydogweb.com/gallery/puppies/labradorretriever2.jpg")
  lazy val img2 = Image("https://img.buzzfeed.com/buzzfeed-static/static/2014-07/28/10/enhanced/webdr11/enhanced-12094-1406557546-35.jpg")
  val imageView = new ImageView {
    src := img1

    // Keep the image centered
    center := ui.center
    middle := ui.middle
  }
  val button = new Button {
    text := "Toggle Image"

    right := ui.right - 50.0
    bottom := ui.bottom - 50.0

    action.attach { evt =>
      if (imageView.src.get.contains(img1)) {
        imageView.src := img2
      } else {
        imageView.src := img1
      }
    }
  }

  children += imageView
  children += button
}
