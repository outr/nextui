package com.outr.nextui.browser

import com.outr.nextui.{Component, Peer}
import org.scalajs.dom.document
import org.scalajs.dom.html.Element

trait ScalaJSComponent extends Peer {
  def component: Component
  def element: Element

  def init(): Unit = {
    component.parent.attach {
      case Some(p) => {
        p.peer.asInstanceOf[ScalaJSContainer].element.appendChild(element)
      }
      case None => // No Parent
    }

    if (component.width.pref.get == 0.0) {
      component.width.pref := element.offsetWidth
    }
    if (component.height.pref.get == 0.0) {
      component.height.pref := element.offsetHeight
    }

    component.x.attach(d => element.style.left = s"${d}px")
    component.y.attach(d => element.style.top = s"${d}px")

  }

  protected def create[T <: Element](tagName: String): T = {
    val e = document.createElement(tagName).asInstanceOf[T]
    e.style.position = "absolute"
    e.style.whiteSpace = "nowrap"
    e
  }
}
