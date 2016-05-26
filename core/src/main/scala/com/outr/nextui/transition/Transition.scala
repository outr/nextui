package com.outr.nextui.transition

import com.outr.nextui._

trait Transition {
  def finished: Boolean
  def init(): Unit
  def invoke(delta: Double): Unit

  def start() = ui.updates.once {
    var first = true
    ui.updates.until(finished) { delta =>
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