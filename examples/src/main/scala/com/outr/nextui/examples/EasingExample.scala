package com.outr.nextui.examples

import com.outr.nextui._
import com.outr.nextui.model.Resource
import com.outr.nextui.transition.easing.Easing

object EasingExample extends UI {
  title := "Easings Example"
  width := 800.0
  height := 600.0

  val image = new ImageView {
    src := Resource("sgine.png")
    center := ui.center
    middle := ui.middle
  }

  import com.outr.nextui.transition._

  forever(
    image.x transitionTo 0.0 in 1.seconds easing Easing.BounceOut
      andThen(image.right transitionTo ui.right.get in 1.seconds easing Easing.SineIn)
      andThen(image.center transitionTo ui.center.get in 1.seconds easing Easing.ElasticOut)
      andThen(image.rotation transitionTo -360.0 in 2.seconds easing Easing.ElasticOut)
      andThen function(image.rotation := 0.0)
  ).start()

  children += image
}