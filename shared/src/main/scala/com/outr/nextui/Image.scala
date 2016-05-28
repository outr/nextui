package com.outr.nextui

import java.net.URL

import pl.metastack.metarx.Sub

class Image extends Component {
  val src: Sub[Option[URL]] = Sub[Option[URL]](None)
}
