package com.outr.nextui.model

import com.outr.nextui.UIImplementation

case class Image(url: String) {
  protected[nextui] lazy val peer: ImagePeer = UIImplementation.peerFor(this)
}

trait ImagePeer