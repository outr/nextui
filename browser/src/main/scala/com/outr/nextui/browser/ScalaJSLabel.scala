package com.outr.nextui.browser

import com.outr.nextui.Label
import org.powerscala.Color
import org.scalajs.dom.raw.HTMLLabelElement

class ScalaJSLabel(val component: Label) extends ScalaJSComponent {
  override val impl: HTMLLabelElement = create[HTMLLabelElement]("label")

  override def initialize(): Unit = {
    super.initialize()

    component.text.attach { s =>
      impl.textContent = s
      updateSize()
    }

    component.font.family.attach(s => impl.style.fontFamily = s)
    component.font.weight.attach(s => impl.style.fontWeight = s.name)
    component.font.style.attach(s => impl.style.fontStyle = s.name)
    component.font.size.attach(d => impl.style.fontSize = s"${d}px")
    component.color.attach(c => impl.style.color = Color.toHex(c))
  }
}
