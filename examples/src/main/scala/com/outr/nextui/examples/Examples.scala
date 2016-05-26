package com.outr.nextui.examples

import com.outr.nextui.UI

object Examples {
  val examples = Vector(
    ContainerExample,
    EasingExample,
    HelloWorld,
    ImageExample,
    ScreensExample,
    VirtualSizeExample
  )

  def apply(): Vector[UI] = examples
}