package com.outr.nextui.examples

import com.outr.nextui.model.Resource
import com.outr.nextui.{Button, ImageView, UI}
import com.outr.scribe.Logging

object AddRemoveExample extends UI with Logging {
  size.width := 800
  size.height := 600

  val image1 = new ImageView {
    src := Resource("sgine.png")
    position.right := ui.position.right - 50.0
    position.top := ui.position.top - 50.0
    size.width.actual.attach { d=>
      logger.info(s"Width: $d")
    }
    size.height.actual.attach { d =>
      logger.info(s"Height: $d")
    }
  }

  children += new Button {
    text := "Add Image 1"
    position.x := 50.0
    position.y := 50.0
    action.attach { evt =>
      if (image1.parent.get.nonEmpty) {
        logger.info("Image 1 has already been added!")
      } else {
        logger.info("Adding Image 1...")
        children += image1
      }
    }
  }
  children += new Button {
    text := "Remove Image 1"
    position.x := 50.0
    position.y := 100.0
    action.attach { evt =>
      if (image1.parent.get.isEmpty) {
        logger.info("Image 1 has already been removed!")
      } else {
        logger.info("Removing Image 1...")
        children -= image1
      }
    }
  }
}