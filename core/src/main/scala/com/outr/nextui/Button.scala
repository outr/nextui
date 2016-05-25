package com.outr.nextui

import com.outr.nextui.event.ActionEvent
import pl.metastack.metarx.{Channel, Sub}

trait Button extends Component {
  lazy val text: Sub[String] = Sub("")

  lazy val action: Channel[ActionEvent] = Channel[ActionEvent]
}
