package com.outr.nextui.model

import com.outr.nextui.UIImplementation

case class Resource(url: String) {
  protected[nextui] lazy val peer: ResourcePeer = UIImplementation.peerFor(this)
}

trait ResourcePeer