package com.outr.nextui.examples

import java.net.URL

import com.outr.nextui.desktop.JavaFX
import com.outr.nextui.{Button, Image, UI}

object ImageExample extends UI with JavaFX {
  title := "Image Example"

  val image = new Image {
    src := Some(getClass.getClassLoader.getResource("tucker.jpg"))

    // Keep the image centered
    center := ui.center
    middle := ui.middle

    // Default the window size to be the size of the image when it loads
    width.actual.attach { d =>
      logger.info(s"Width changed: $d")
    }
    ui.width.pref.attach { d =>
      logger.info(s"Pref Width Changed: $d")
    }
    ui.width.pref := width.actual
    ui.height.pref := height.actual
  }
  val button = new Button {
    text := "Change Image"

    right := ui.right - 50.0
    bottom := ui.bottom - 50.0

    action.attach { evt =>
      image.src := new URL("https://img.buzzfeed.com/buzzfeed-static/static/2014-07/28/10/enhanced/webdr11/enhanced-12094-1406557546-35.jpg")
    }
  }

  children += image
  children += button
}
