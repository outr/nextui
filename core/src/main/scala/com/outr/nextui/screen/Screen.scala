package com.outr.nextui.screen

import com.outr.nextui.{Container, UI}
import com.outr.nextui.transition._
import pl.metastack.metarx.{ReadStateChannel, Sub, Var}

class Screen(implicit ui: UI) extends Container {
  private[screen] val _status = Var[ScreenStatus](ScreenStatus.Removed)
  val status: ReadStateChannel[ScreenStatus] = _status
  val transitionIn: Sub[Option[Transition]] = Sub(None)
  val transitionOut: Sub[Option[Transition]] = Sub(None)

  protected[screen] def activate(screens: Screens): Unit = {
    size.width := screens.size.width.actual
    size.height := screens.size.height.actual
    if (!screens.children.get.contains(this)) {
      screens.children += this
    }
    _status := ScreenStatus.TransitioningIn
    transitionIn.get match {
      case Some(t) => function(visible := true).andThen(t.andThen(function(_status := ScreenStatus.Active))).start()
      case None => {
        visible := true
        _status := ScreenStatus.Active
      }
    }
  }

  protected[screen] def remove(screens: Screens): Unit = {
    _status := ScreenStatus.TransitioningOut
    val f = () => {
      visible := false
      screens.children -= this
      _status := ScreenStatus.Removed
    }
    transitionOut.get match {
      case Some(t) => t.andThen(function(f())).start()
      case None => f()
    }
  }
}