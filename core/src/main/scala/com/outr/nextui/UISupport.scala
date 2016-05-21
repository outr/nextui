package com.outr.nextui

trait UISupport extends UI {
  UISupport.implementation match {
    case Some(impl) => throw new RuntimeException(s"Unable to register more than one implementation. Currently registered: ${impl.getClass.getSimpleName}, Registering: ${getClass.getSimpleName}.")
    case None => UISupport.implementation = Some(this)
  }

  def init(component: Component): Unit
}

object UISupport {
  private var implementation: Option[UISupport] = None

  def init(component: Component): Unit = implementation match {
    case Some(impl) => impl.init(component)
    case None => throw new RuntimeException(s"No implementation defined for UISupport.")
  }
}