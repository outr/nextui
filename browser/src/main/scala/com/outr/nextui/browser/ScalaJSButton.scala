package com.outr.nextui.browser

import com.outr.nextui.Button
import org.scalajs.dom.raw.HTMLButtonElement

class ScalaJSButton(val component: Button) extends ScalaJSComponent {
  override val element: HTMLButtonElement = create[HTMLButtonElement]("button")

  override def init(): Unit = {
    super.init()

    component.text.attach(s => element.textContent = s)
  }
}
