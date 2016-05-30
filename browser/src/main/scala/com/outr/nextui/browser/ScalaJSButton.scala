package com.outr.nextui.browser

import com.outr.nextui.Button
import com.outr.nextui.event.ActionEvent
import org.scalajs.dom._
import org.scalajs.dom.raw.HTMLButtonElement

class ScalaJSButton(val component: Button) extends ScalaJSComponent {
  override val impl: HTMLButtonElement = create[HTMLButtonElement]("button")

  override def init(): Unit = {
    super.init()

    component.text.attach { s =>
      impl.textContent = s
      updateSize()
    }
    impl.addEventListener("click", {(evt: Event) =>
      component.action := new ActionEvent
    }, true)
  }
}
