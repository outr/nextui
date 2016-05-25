package com.outr.nextui

import pl.metastack.metarx.{ReadStateChannel, Var}

trait Component {
  lazy val peer: Peer = UIImplementation.peerFor(this)

  protected[nextui] val _parent = Var[Option[Container]](None)
  def parent: ReadStateChannel[Option[Container]] = _parent
}