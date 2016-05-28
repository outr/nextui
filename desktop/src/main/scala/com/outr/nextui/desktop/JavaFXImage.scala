package com.outr.nextui.desktop

import com.outr.nextui.Image

class JavaFXImage(val component: Image) extends JavaFXComponent {
  override val impl: javafx.scene.image.ImageView = new javafx.scene.image.ImageView

  override def init(): Unit = {
    super.init()

    component.src.attach { urlOption =>
      urlOption.map(url => new javafx.scene.image.Image(url.toString)) match {
        case Some(img) => {
          component.width._actual := img.getWidth
          component.height._actual := img.getHeight
          impl.setImage(img)
        }
        case None => {
          component.width._actual := 0.0
          component.height._actual := 0.0
          impl.setImage(None.orNull)
        }
      }
    }
  }
}
