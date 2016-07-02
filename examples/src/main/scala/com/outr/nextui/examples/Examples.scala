package com.outr.nextui.examples

import com.outr.nextui.UI

object Examples {
  val examples = Vector(
    HelloWorld,
    ContainerExample,
    EasingExample,
    ImageExample,
    LabelExample,
    ScreensExample,
    VirtualSizeExample,
    AddRemoveExample
  )

  def apply(): Vector[UI] = examples
}