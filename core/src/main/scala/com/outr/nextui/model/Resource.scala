package com.outr.nextui.model

import com.outr.nextui.UIImplementation

class Resource(protected[nextui] val path: String) {
  protected[nextui] lazy val peer: ResourcePeer = UIImplementation.peerFor(this)

  def url: String = peer.url
}

object Resource {
  val Empty: Resource = new Resource("")

  def apply(path: String): Resource = new Resource(path)
}

trait ResourcePeer {
  def url: String
}