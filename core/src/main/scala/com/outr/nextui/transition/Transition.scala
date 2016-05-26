package com.outr.nextui.transition

import com.outr.nextui.UI

trait Transition {
  def finished: Boolean
  def init(): Unit
  def invoke(delta: Double): Unit

  def start(context: UI) = context.updates.once {
    var first = true
    context.updates.until(finished) { delta =>
      if (first) {
        init()
        first = false
      }
      invoke(delta)
    }
  }

  def andThen(next: Transition): Sequence = new Sequence(List(this, next))
  def and(other: Transition): Parallel = new Parallel(Set(this, other))
}