package com.outr.nextui

object UIImplementation {
  // TODO: better handle
  var instance: Option[UIImplementation] = None

  def peerFor(component: Component): Peer = {
    val impl = instance.getOrElse(throw new RuntimeException(s"No UIImplementation defined!"))
    impl.peerFor(component).getOrElse(throw new UnsupportedOperationException(s"Component of type ${component.getClass.getName} is not supported."))
  }
}

trait UIImplementation {
  def peerFor(component: Component): Option[Peer]
}