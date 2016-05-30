package com.outr.nextui

import com.outr.nextui.model.{Image, Resource}
import pl.metastack.metarx.Sub

class ImageView extends Component {
  val image: Sub[Option[Image]] = Sub[Option[Image]](None)
  val preserveAspectRatio: Sub[Boolean] = Sub(true)

  object src {
    def :=(resource: Resource): Unit = image := Some(Image(resource.url))
  }
}