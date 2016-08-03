package com.outr.nextui.browser

import com.outr.nextui.ImageView
import com.outr.nextui.model.Image
import org.scalajs.dom.Event
import org.scalajs.dom.raw.HTMLImageElement

class ScalaJSImageView(val component: ImageView) extends ScalaJSComponent {
  override val impl: HTMLImageElement = create[HTMLImageElement]("img")

  override def initialize(): Unit = {
    super.initialize()

    component.image.attach {
      case img if img != Image.Empty => impl.src = img.resource.url.toString
      case _ => impl.src = ""
    }

    impl.addEventListener("load", {(evt: Event) =>
      updateSize(delayed = false)
    }, true)
  }
}
