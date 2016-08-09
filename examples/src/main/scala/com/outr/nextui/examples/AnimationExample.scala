package com.outr.nextui.examples

import com.outr.nextui.model.{Image, Resource}
import com.outr.nextui.{Animation, UI}

object AnimationExample extends UI {
  title := "Animation Example"
  size.width := 1024
  size.height := 768

  val animation = new Animation(Resource("animation.png"), 144, 144, frameRate = 0.05)
  children += animation
}
