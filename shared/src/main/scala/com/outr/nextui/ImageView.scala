package com.outr.nextui

import com.outr.nextui.model.Image
import pl.metastack.metarx.Sub

class ImageView extends Component {
  val src: Sub[Option[Image]] = Sub[Option[Image]](None)
}