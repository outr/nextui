package com.outr.nextui

import pl.metastack.metarx.{ReadChannel, Var}

trait Component {
  protected[nextui] val _parent = Var[Option[Component]](None)
  def parent: ReadChannel[Option[Component]] = _parent
}