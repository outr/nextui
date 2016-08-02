package com.outr.nextui

import pl.metastack.metarx.{Dep, Sub}

package object transition {
  def delay(time: Double): Delay = new Delay(time)

  def repeat(times: Int, transition: Transition): Repeat = new Repeat(times, transition)

  def forever(transition: Transition): Repeat = new Repeat(Int.MaxValue, transition)

  def function(f: => Unit): ActionTransition = new ActionTransition((d: Double) => f)

  implicit class Transitions(sub: Sub[Double]) {
    def transitionTo(to: => Double): TransitionTo = {
      TransitionTo((d: Double) => sub := d, () => sub.get, () => to)
    }
  }

  implicit class DepTransitions(dep: Dep[Double, Double]) {
    def transitionTo(to: => Double): TransitionTo = {
      TransitionTo((d: Double) => dep := d, () => dep.get, () => to)
    }
  }
}
