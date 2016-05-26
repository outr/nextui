package com.outr.nextui.transition

class Delay(duration: Double) extends Transition {
  private var elapsed: Double = 0.0

  override def init(): Unit = elapsed = 0.0

  override def invoke(delta: Double): Unit = elapsed += delta

  override def finished: Boolean = elapsed >= duration
}
