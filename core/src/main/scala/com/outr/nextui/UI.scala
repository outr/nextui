package com.outr.nextui

import pl.metastack.metarx.Sub

trait UI {
  val currentScene: Sub[Option[Scene]] = Sub(None)

  val title: Sub[String] = Sub("")
}
