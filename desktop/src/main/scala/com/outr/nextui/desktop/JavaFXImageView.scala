package com.outr.nextui.desktop

import com.outr.nextui.ImageView

class JavaFXImageView(val component: ImageView) extends JavaFXComponent {
  override val impl: javafx.scene.image.ImageView = new javafx.scene.image.ImageView

  override def initialize(): Unit = {
    super.initialize()

//    impl.setSmooth(true)

    component.preserveAspectRatio.attach(impl.setPreserveRatio)
    doubleBindOption(component.size.width.pref, impl.setFitWidth, impl.fitWidthProperty())
    doubleBindOption(component.size.height.pref, impl.setFitHeight, impl.fitHeightProperty())

    component.image.attach {
      case Some(image) => {
        val img = image.peer.asInstanceOf[javafx.scene.image.Image]
        component.size.width._actual := img.getWidth
        component.size.height._actual := img.getHeight
        impl.setImage(img)
      }
      case None => {
        component.size.width._actual := 0.0
        component.size.height._actual := 0.0
        impl.setImage(None.orNull)
      }
    }
  }
}
