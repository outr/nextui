package com.outr.nextui.screen

import com.outr.nextui._
import pl.metastack.metarx.Sub

class Screens extends Container {
  val active: Sub[Option[Screen]] = Sub(None)

  // Default to the size of the UI
  size.width := ui.size.width.actual
  size.height := ui.size.height.actual

  active.attach { screenOption =>
    children.get.foreach {
      case screen: Screen if screen.status.get == ScreenStatus.Active => screen.remove(this)
      case _ => // Ignore other items
    }
    screenOption match {
      case Some(screen) => screen.activate(this)
      case None => // Nothing new to set
    }
  }
}
