package com.outr.nextui.model

import com.outr.nextui.UIImplementation

class Image private[nextui](val resource: Resource, val width: Double, val height: Double) {
  protected[nextui] lazy val peer: ImagePeer = UIImplementation.peerFor(this)

  override def equals(obj: scala.Any): Boolean = obj match {
    case i: Image => i.resource == resource
    case _ => false
  }
}

class UpdatedImage private[nextui](resource: Resource, width: Double, height: Double, p: ImagePeer) extends Image(resource, width, height) {
  override protected[nextui] lazy val peer: ImagePeer = p
}

object Image {
  val Empty = Image(Resource.Empty)

  def apply(url: String): Image = Image(Resource(url))
  def apply(resource: Resource): Image = new Image(resource, 0.0, 0.0)
}

trait ImagePeer