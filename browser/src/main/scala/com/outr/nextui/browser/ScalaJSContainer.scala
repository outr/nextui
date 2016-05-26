package com.outr.nextui.browser

import org.scalajs.dom.raw.HTMLDivElement

trait ScalaJSContainer extends ScalaJSComponent {
  override val impl: HTMLDivElement = create[HTMLDivElement]("div")
}
