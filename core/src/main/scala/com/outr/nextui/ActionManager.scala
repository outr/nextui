package com.outr.nextui

import scala.collection.mutable.ListBuffer

class ActionManager {
  private val queue = ListBuffer.empty[Action]

  def on(f: => Unit): Action = synchronized {
    val a = new Action {
      override def exec(delta: Double): Boolean = {
        f
        true
      }
    }
    queue += a
    a
  }

  def apply(f: Double => Unit): Action = synchronized {
    val a = new Action {
      override def exec(delta: Double): Boolean = {
        f(delta)
        true
      }
    }
    queue += a
    a
  }

  def once(f: => Unit): Action = synchronized {
    val a = new Action {
      override def exec(delta: Double): Boolean = {
        f
        false
      }
    }
    queue += a
    a
  }

  def until(condition: => Boolean)(f: Double => Unit): Action = {
    var action: Action = null
    action = apply { delta =>
      f(delta)
      if (condition) {
        remove(action)
      }
    }
    action
  }

  def in(delay: Double)(f: => Unit): Action = every(delay, stopIn = delay)(f)

  def every(delay: Double, runNow: Boolean = false, stopIn: Double = Int.MaxValue.toDouble)(f: => Unit): Action = {
    var total = 0.0
    var elapsed = if (runNow) delay else 0.0
    var action: Action = null
    action = apply { delta =>
      elapsed += delta
      total += delta
      if (elapsed >= delay) {
        f
        elapsed = 0.0
      }
      if (total > stopIn) {
        remove(action)
      }
    }
    action
  }

  def remove(action: Action) = synchronized {
    queue -= action
  }

  def exec(delta: Double): Unit = synchronized {
    queue.foreach(action => execAction(delta, action))
  }

  private val execAction = (delta: Double, action: Action) => {
    val survive = action.exec(delta)
    if (!survive) {
      queue -= action
    }
  }

  def nonEmpty: Boolean = queue.nonEmpty
  def isEmpty: Boolean = queue.isEmpty

  def clear(): Unit = {
    println("Clearing!")
    queue.clear()
  }
}

/**
  * Actions are invoked on ActionManager.
  */
trait Action {
  /**
    * Executes the action and returns true if the action should continue to run (false removes it from further execution)
    *
    * @param delta the time in seconds since the last update
    * @return true if the action should continue to live
    */
  def exec(delta: Double): Boolean
}