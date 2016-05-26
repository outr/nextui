package com.outr.nextui.transition

class ActionTransition(action: Double => Unit) extends Transition {
  private var hasRun = false

  override def init(): Unit = {
    hasRun = false
  }

  override def finished: Boolean = hasRun

  override def invoke(delta: Double): Unit = {
    action(delta)
    hasRun = true
  }
}