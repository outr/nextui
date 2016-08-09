package com.outr.nextui

import com.outr.nextui.model.{Image, Resource}
import com.outr.scribe.Logging
import pl.metastack.metarx.Sub

class Animation extends ImageView with Logging {
  def this(resource: Resource,
           frameWidth: Int,
           frameHeight: Int,
           frameTotal: Int = -1,
           frameRate: Double = 0.1) = {
    this()
    frame.width := frameWidth
    frame.height := frameHeight
    frame.total := frameTotal
    frame.rate := frameRate
    image := Image(resource)
  }

  object frame {
    val width: Sub[Int] = Sub(-1)
    val height: Sub[Int] = Sub(-1)
    val total: Sub[Int] = Sub(-1)
    val rate: Sub[Double] = Sub(0.1)
  }

  private var delay = 0.0
  private var index = 0
  private var offsetX = 0.0
  private var offsetY = 0.0

  updates { delta =>
    delay += delta
    if (delay >= frame.rate.get) {
      delay = 0.0
      image.get match {
        case Image.Empty => // Ignore
        case img => {
          assert(frame.width.get > 0, "Animation.frame.width must be set to a positive value.")
          assert(frame.height.get > 0, "Animation.frame.height must be set to a positive value.")

          if (index >= frame.total.get && frame.total.get > 0) {
            index = 0
            offsetX = 0.0
            offsetY = 0.0
          } else {
            offsetX += frame.width.get
            if (offsetX >= img.width) {
              offsetX = 0.0
              offsetY += frame.height.get
              if (offsetY >= img.height) {
                offsetY = 0.0
                index = 0
              }
            }
          }
          clip.x1 := offsetX
          clip.y1 := offsetY
          clip.x2 := offsetX + frame.width.get
          clip.y2 := offsetY + frame.height.get
          index += 1
        }
      }
    }
  }

  image.attach { img =>
    logger.info(s"Image: ${img.width}x${img.height}")
  }
}