package com.outr.nextui.browser

import com.outr.nextui.{Component, Container}
import org.scalajs.dom.raw.HTMLDivElement

trait ScalaJSContainer extends ScalaJSComponent {
  override val impl: HTMLDivElement = create[HTMLDivElement]("div")
}

object ScalaJSContainer {
  def apply(container: Container): ScalaJSContainer = new ScalaJSContainer {
    override val component: Component = container
  }
}