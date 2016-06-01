package com.outr.nextui.examples

import com.outr.nextui.browser.ScalaJS
import com.outr.nextui.model.Image
import com.outr.nextui.{ImageView, UI, VirtualMode, VirtualSizeSupport}

object VirtualSizeExample extends UI with ScalaJS with VirtualSizeSupport {
  virtualWidth := 450.0
  virtualHeight := 338.0
  virtualMode := VirtualMode.Bars

  children += new ImageView {
    image := Image("https://puppydogweb.com/gallery/puppies/labradorretriever2.jpg")
    x := 0.0.vx
    y := 0.0.vy
    width := 450.0.vw
    height := 338.0.vh
  }
}
