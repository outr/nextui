package com.outr.nextui.model

import java.net.URL

import com.outr.nextui.UIImplementation

case class Image(url: URL) {
  protected[nextui] lazy val peer: ImagePeer = UIImplementation.peerFor(this)
}

object Image {
  def apply(url: String): Image = apply(new URL(url))
}

trait ImagePeer