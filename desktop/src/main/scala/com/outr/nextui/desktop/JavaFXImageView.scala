package com.outr.nextui.desktop

import com.outr.nextui.ImageView

class JavaFXImageView(val component: ImageView) extends JavaFXComponent {
  override val impl: javafx.scene.image.ImageView = new javafx.scene.image.ImageView

  override def initialize(): Unit = {
    super.initialize()

//    impl.setSmooth(true)

    component.preserveAspectRatio.attach(impl.setPreserveRatio)
    doubleBindOption(component.width.pref, impl.setFitWidth, impl.fitWidthProperty())
    doubleBindOption(component.height.pref, impl.setFitHeight, impl.fitHeightProperty())

    component.image.attach {
      case Some(image) => {
        val img = image.peer.asInstanceOf[javafx.scene.image.Image]
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
