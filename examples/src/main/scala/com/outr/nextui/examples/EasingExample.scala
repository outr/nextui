package com.outr.nextui.examples

import com.outr.nextui._
import com.outr.nextui.model.Resource
import com.outr.nextui.transition.easing.Easing

object EasingExample extends UI {
  title := "Easings Example"
  size.width := 800.0
  size.height := 600.0

  val image = new ImageView {
    src := Resource("sgine.png")
    position.center := ui.position.center
    position.middle := ui.position.middle
  }

  override def init(): Unit = {
    import com.outr.nextui.transition._

    forever(
      image.position.x transitionTo 0.0 in 1.seconds easing Easing.BounceOut
        andThen (image.position.right transitionTo ui.position.right.get in 1.seconds easing Easing.SineIn)
        andThen (image.position.center transitionTo ui.position.center.get in 1.seconds easing Easing.ElasticOut)
        andThen (image.rotation transitionTo -360.0 in 2.seconds easing Easing.ElasticOut)
        andThen function(image.rotation := 0.0)
    ).start()

    children += image
  }
}