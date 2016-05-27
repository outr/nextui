package com.outr.nextui.browser

import com.outr.nextui.{Component, Peer}
import org.scalajs.dom._
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
    updateSize()
    component.width.pref.attach(d => if (d != 0.0) {
      element.style.width = s"${d}px"
      component.width._actual := d
    })
    component.height.pref.attach(d => if (d != 0.0) {
      element.style.height = s"${d}px"
      component.height._actual := d
    })
  }

  protected def create[T <: Element](tagName: String): T = {
    val e = document.createElement(tagName).asInstanceOf[T]
    e.style.position = "absolute"
    e.style.whiteSpace = "nowrap"
    e
  }

  private val updateFunction = () => {
    if (element.offsetWidth != component.width.actual.get) {
      component.width._actual := element.offsetWidth
    }
    if (element.offsetHeight != component.height.actual.get) {
      component.height._actual := element.offsetHeight
    }
  }
  def updateSize(delayed: Boolean = true): Unit = if (delayed) {
    window.setTimeout(updateFunction, 0.01)
  } else {
    updateFunction()
  }
}
