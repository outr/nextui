package com.outr.nextui

import com.outr.nextui.model.{Image, ImagePeer, Resource, ResourcePeer}

object UIImplementation {
  private var instance: Option[UIImplementation] = None
  private def set(impl: UIImplementation): Unit = synchronized {
    instance match {
      case Some(existing) => throw new RuntimeException(s"Implementation already defined: $existing when trying to set: $impl.")
      case None => instance = Option(impl)
    }
  }

  def peerFor(component: Component): Peer[_] = {
    val impl = instance.getOrElse(throw new RuntimeException(s"No UIImplementation defined!"))
    impl.peerFor(component).getOrElse(throw new UnsupportedOperationException(s"Component of type ${component.getClass.getName} is not supported."))
  }

  def peerFor(resource: Resource): ResourcePeer = {
    val impl = instance.getOrElse(throw new RuntimeException(s"No UIImplementation defined!"))
    impl.peerFor(resource)
  }

  def peerFor(image: Image): ImagePeer = {
    val impl = instance.getOrElse(throw new RuntimeException(s"No UIImplementation defined!"))
    impl.peerFor(image)
  }
}

trait UIImplementation {
  UIImplementation.set(this)

  def peerFor(component: Component): Option[Peer[_]]

  def peerFor(resource: Resource): ResourcePeer

  def peerFor(image: Image): ImagePeer
}