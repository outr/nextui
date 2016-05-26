package com.outr.nextui.transition

class Repeat(times: Int = 1, transition: Transition) extends Transition {
  private var counter = 0

  override def init(): Unit = {
    counter = 0
    transition.init()
  }

  override def invoke(delta: Double): Unit = {
    transition.invoke(delta)
  }

  override def finished: Boolean = if (transition.finished) {
    if (counter == times) {
      true
    } else {
      counter += 1
      transition.init()
      false
    }
  } else {
    false
  }
}