package com.outr.nextui

import com.outr.nextui.event.ActionEvent
import pl.metastack.metarx.{Channel, ReadStateChannel, Var}

trait Component {
  protected[nextui] val _parent = Var[Option[Component]](None)
  def parent: ReadStateChannel[Option[Component]] = _parent

  protected[nextui] var peer: Option[AnyRef] = None

  object events {
    lazy val action: Channel[ActionEvent] = Channel[ActionEvent]
  }
}