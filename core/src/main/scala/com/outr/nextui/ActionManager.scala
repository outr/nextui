package com.outr.nextui

import java.util.concurrent.atomic.AtomicReference

import scala.collection.mutable.ListBuffer

class ActionManager(name: String) {
  private val queue = ListBuffer.empty[Action]

  def on(f: => Unit): Action = synchronized {
    val a = new Action((d: Double) => f, once = false)
    queue += a
    a
  }

  def apply(f: Double => Unit): Action = synchronized {
    val a = new Action(f, once = false)
    queue += a
    a
  }

  def once(f: => Unit): Action = synchronized {
    val a = new Action((d: Double) => f, once = true)
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
    ActionManager.current.set(name)
    try {
      queue.foreach(action => execAction(delta, action))
    } finally {
      ActionManager.current.set("")
    }
  }

  private val execAction = (delta: Double, action: Action) => {
    action.invoke(delta)
    if (action.once) {
      queue -= action
    }
  }

  def nonEmpty: Boolean = queue.nonEmpty
  def isEmpty: Boolean = queue.isEmpty

  def clear(): Unit = queue.clear()
}

class Action(f: (Double) => Unit, val once: Boolean) {
  def invoke(delta: Double): Unit = f(delta)
}

object ActionManager {
  private val current = new AtomicReference[String]("")
}