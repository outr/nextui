package com.outr.nextui.browser

import org.scalajs.dom.raw.HTMLDivElement

trait ScalaJSContainer extends ScalaJSComponent {
  override val element: HTMLDivElement = create[HTMLDivElement]("div")
}
