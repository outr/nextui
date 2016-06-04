package com.outr.nextui.screen

import com.outr.nextui.Container
import pl.metastack.metarx.{ReadStateChannel, Var}

class Screen extends Container {
  private[screen] val _status = Var[ScreenStatus](ScreenStatus.Removed)
  val status: ReadStateChannel[ScreenStatus] = _status

  protected[screen] def activate(screens: Screens): Unit = {
    // TODO: support transitioning
    width := screens.width.actual
    height := screens.height.actual
//    visible := true
    if (!screens.children.get.contains(this)) {
      screens.children += this
    }
    _status := ScreenStatus.TransitioningIn
    _status := ScreenStatus.Active
  }

  protected[screen] def remove(screens: Screens): Unit = {
    // TODO: support transitioning
    _status := ScreenStatus.TransitioningOut
//    visible := false
    screens.children -= this
    _status := ScreenStatus.Removed
  }
}