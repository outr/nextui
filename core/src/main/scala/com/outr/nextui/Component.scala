package com.outr.nextui

import pl.metastack.metarx.{ReadStateChannel, Var}

trait Component {
  protected[nextui] val _parent = Var[Option[Container[_ <: Component]]](None)
  def parent: ReadStateChannel[Option[Container[_ <: Component]]] = _parent
}