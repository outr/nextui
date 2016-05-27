package com.outr.nextui.browser

import com.outr.nextui.{Button, Component, Peer, UI, UIImplementation}
import org.scalajs.dom.{Event, document, window}

import scala.scalajs.js.JSApp

trait ScalaJS extends JSApp with ScalaJSContainer with UIImplementation {
  this: UI =>

  override def component: Component = this

  def main(): Unit = {
    println("Starting ScalaJS Application...")
    initialize()
  }

  def initialize(): Unit = {
    allChildren.map(_.peer).foreach {
      case sjs: ScalaJSComponent => sjs.init()
      case c => throw new RuntimeException(s"Component peer is not a ScalaJSComponent: $c.")
    }

    width._actual := window.innerWidth.toDouble
    height._actual := window.innerHeight.toDouble
    window.addEventListener("resize", { (evt: Event) =>
      width._actual := window.innerWidth.toDouble
      height._actual := window.innerHeight.toDouble
    }, true)

    document.body.appendChild(element)
  }

  override def peerFor(component: Component): Option[Peer] = component match {
    case b: Button => Some(new ScalaJSButton(b))
    case js: ScalaJS => Some(js)
    case _ => None
  }
}
