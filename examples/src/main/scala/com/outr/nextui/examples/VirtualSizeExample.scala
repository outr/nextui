package com.outr.nextui.examples

import com.outr.nextui.model.Resource
import com.outr.nextui.{ImageView, UI, VirtualMode, VirtualSizeSupport}

object VirtualSizeExample extends UI with VirtualSizeSupport {
  virtualWidth := 1024.0
  virtualHeight := 768.0
  virtualMode := VirtualMode.Bars

  children += new ImageView {
    src := Resource("1024.jpg")
    x := 0.0.vx
    y := 0.0.vy
    width := 1024.0.vw
    height := 768.0.vh
    preserveAspectRatio := false
  }
}