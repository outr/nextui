package com.outr.nextui.model

import java.net.URL

import com.outr.nextui.UIImplementation

case class Resource(url: URL) {
  protected[nextui] lazy val peer: ResourcePeer = UIImplementation.peerFor(this)
}

object Resource {
  def apply(url: String): Resource = apply(new URL(url))
}

trait ResourcePeer