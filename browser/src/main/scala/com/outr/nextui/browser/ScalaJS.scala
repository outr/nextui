package com.outr.nextui.browser

import com.outr.nextui._
import com.outr.nextui.model.{Image, ImagePeer, Resource, ResourcePeer}
import com.outr.scribe.Logging
import org.scalajs.dom.raw.HTMLImageElement
import org.scalajs.dom.{Event, document, window}

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

trait ScalaJS extends JSApp with ScalaJSContainer with UIImplementation with Logging {
  def ui: UI

  override val component: Component = ui

  def main(): Unit = {
    logger.info("Starting ScalaJS Application...")
    start()
  }

  def start(): Unit = {
    ui.allChildren.map(_.peer).foreach {
      case sjs: ScalaJSComponent => sjs.init()
      case c => throw new RuntimeException(s"Component peer is not a ScalaJSComponent: $c.")
    }

    impl.style.width = "100%"
    impl.style.height = "100%"

    ui.title.attach(t => document.title = t)
    ui.fullScreen.attach { fs =>
      if (fs) logger.warn(s"Fullscreen not yet supported in Scala.js implementation.")
    }
    ui.width._actual := window.innerWidth.toDouble
    ui.height._actual := window.innerHeight.toDouble
    window.addEventListener("resize", { (evt: Event) =>
      ui.width._actual := window.innerWidth.toDouble
      ui.height._actual := window.innerHeight.toDouble
    }, true)

    var previous = System.currentTimeMillis()
    var updateFunction: Double => Unit = null
    updateFunction = (timestamp: Double) => {
      val current = System.currentTimeMillis()
      val delta = (current - previous) / 1000.0
      ui.update(delta)
      previous = current
      ui.update(delta)
      window.requestAnimationFrame(updateFunction)
    }
    window.requestAnimationFrame(updateFunction)

    document.body.appendChild(impl)
  }

  override def peerFor(component: Component): Option[Peer[_]] = component match {
    case b: Button => Some(new ScalaJSButton(b))
    case i: ImageView => Some(new ScalaJSImageView(i))
    case l: Label => Some(new ScalaJSLabel(l))
    case ui: UI => Some(this)
    case c: Container => Some(ScalaJSContainer(c))
    case _ => None
  }

  override def peerFor(resource: Resource): ResourcePeer = new ScalaJSResource(resource)

  override def peerFor(image: Image): ImagePeer = new ScalaJSImage(image)
}

class ScalaJSResource(resource: Resource) extends ResourcePeer {
  lazy val url: String = resource.path
}

class ScalaJSImage(image: Image) extends ImagePeer {
  val element = document.createElement("img").asInstanceOf[HTMLImageElement]
  element.src = image.resource.url.toString
}

@JSExport
object ScalaJS {
  def apply(userInterface: UI): ScalaJS = {
    val sjs = new ScalaJS {
      override def ui: UI = userInterface
    }
    sjs.main()
    sjs
  }
}