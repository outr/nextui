package com.outr.nextui

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
}

trait UIImplementation {
  UIImplementation.set(this)

  def peerFor(component: Component): Option[Peer[_]]
}