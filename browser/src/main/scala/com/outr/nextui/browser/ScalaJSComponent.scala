package com.outr.nextui.browser

import com.outr.nextui.Peer
import org.scalajs.dom._
import org.scalajs.dom.html.Element

trait ScalaJSComponent extends Peer[Element] {
  override def init(): Unit = {
    component.parent.attach {
      case Some(p) => {
        p.peer.asInstanceOf[ScalaJSContainer].impl.appendChild(impl)
      }
      case None => // No Parent
    }

    if (component.width.pref.get == 0.0) {
      component.width.pref := impl.offsetWidth
    }
    if (component.height.pref.get == 0.0) {
      component.height.pref := impl.offsetHeight
    }

    component.x.attach(d => impl.style.left = s"${d}px")
    component.y.attach(d => impl.style.top = s"${d}px")
    updateSize()
    component.width.pref.attach(d => if (d != 0.0) {
      impl.style.width = s"${d}px"
      component.width._actual := d
    })
    component.height.pref.attach(d => if (d != 0.0) {
      impl.style.height = s"${d}px"
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
    if (impl.offsetWidth != component.width.actual.get) {
      component.width._actual := impl.offsetWidth
    }
    if (impl.offsetHeight != component.height.actual.get) {
      component.height._actual := impl.offsetHeight
    }
  }
  def updateSize(delayed: Boolean = true): Unit = if (delayed) {
    window.setTimeout(updateFunction, 0.01)
  } else {
    updateFunction()
  }
}
