package com.outr.nextui.browser

import com.outr.nextui.Peer
import org.powerscala.Color
import org.scalajs.dom._
import org.scalajs.dom.html.Element

trait ScalaJSComponent extends Peer[Element] {
  override def initialize(): Unit = {
    if (component.size.width.pref.get.isEmpty && impl.offsetWidth > 0.0) {
      component.size.width.pref := Option(impl.offsetWidth)
    }
    if (component.size.height.pref.get.isEmpty && impl.offsetHeight > 0.0) {
      component.size.height.pref := Option(impl.offsetHeight)
    }

    component.position.x.attach(d => impl.style.left = s"${d}px")
    component.position.y.attach(d => impl.style.top = s"${d}px")
    component.rotation.attach(d => impl.style.transform = s"rotate(${d}deg)")
    updateSize()
    component.size.width.pref.attach {
      case Some(d) => {
        impl.style.width = s"${d}px"
        component.size.width._actual := d
      }
      case None => // Ignore
    }
    component.size.height.pref.attach {
      case Some(d) => {
        impl.style.height = s"${d}px"
        component.size.height._actual := d
      }
      case None => // Ignore
    }

    component.background.attach { c =>
      if (c != Color.Clear) {
        impl.style.backgroundColor = Color.toHex(c)
      } else {
        impl.style.backgroundColor = ""
      }
    }
  }

  protected def create[T <: Element](tagName: String): T = {
    val e = document.createElement(tagName).asInstanceOf[T]
    e.style.position = "absolute"
    e.style.whiteSpace = "nowrap"
    e
  }

  private val updateFunction = () => {
    if (impl.offsetWidth != component.size.width.actual.get) {
      component.size.width._actual := impl.offsetWidth
    }
    if (impl.offsetHeight != component.size.height.actual.get) {
      component.size.height._actual := impl.offsetHeight
    }
  }
  def updateSize(delayed: Boolean = true): Unit = if (delayed) {
    window.setTimeout(updateFunction, 0.01)
  } else {
    updateFunction()
  }
}
