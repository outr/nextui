package com.outr.nextui.browser

import com.outr.nextui.ImageView
import org.scalajs.dom.Event
import org.scalajs.dom.raw.HTMLImageElement

class ScalaJSImageView(val component: ImageView) extends ScalaJSComponent {
  override val impl: HTMLImageElement = create[HTMLImageElement]("img")

  override def init(): Unit = {
    super.init()

    component.image.attach {
      case Some(img) => impl.src = img.url.toString
      case None => impl.src = ""
    }

    impl.addEventListener("load", {(evt: Event) =>
      updateSize(delayed = false)
    }, true)
  }
}
