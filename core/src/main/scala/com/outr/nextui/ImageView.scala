package com.outr.nextui

import com.outr.nextui.model.{Image, Resource}
import pl.metastack.metarx.Sub

class ImageView extends Component {
  val image: Sub[Image] = Sub[Image](Image.Empty)
  val preserveAspectRatio: Sub[Boolean] = Sub(true)
  object clip {
    val x1: Sub[Option[Double]] = Sub(None)
    val y1: Sub[Option[Double]] = Sub(None)
    val x2: Sub[Option[Double]] = Sub(None)
    val y2: Sub[Option[Double]] = Sub(None)
  }

  object src {
    def :=(resource: Resource): Unit = image := Image(resource)
  }
}