package com.outr.nextui.model

import com.outr.nextui.UIImplementation

case class Image(resource: Resource) {
  protected[nextui] lazy val peer: ImagePeer = UIImplementation.peerFor(this)
}

object Image {
  def apply(url: String): Image = Image(Resource(url))
}

trait ImagePeer