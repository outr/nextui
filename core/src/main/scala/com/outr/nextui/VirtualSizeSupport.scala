package com.outr.nextui

import pl.metastack.metarx._

trait VirtualSizeSupport extends Component {
  val virtualWidth: Sub[Double] = Sub(1024.0)
  val virtualHeight: Sub[Double] = Sub(768.0)
  val virtualMode: Sub[VirtualMode] = Sub(VirtualMode.Bars)

  lazy val virtual = new VirtualSize(this)

  implicit class IntVirtualPixels(i: Int) {
    def vx: ReadChannel[Double] = vw + virtual.xOffset
    def vy: ReadChannel[Double] = vh + virtual.yOffset
    def vw: ReadChannel[Double] = i.toDouble * virtual.wMulti
    def vh: ReadChannel[Double] = i.toDouble * virtual.hMulti
    def vf: ReadChannel[Int] = (i.toDouble * virtual.wMulti).map(d => math.round(d).toInt)
  }
  implicit class DoubleVirtualPixels(d: Double) {
    def vx: ReadChannel[Double] = vw + virtual.xOffset
    def vy: ReadChannel[Double] = vh + virtual.yOffset
    def vw: ReadChannel[Double] = d * virtual.wMulti
    def vh: ReadChannel[Double] = d * virtual.hMulti
    def vf: ReadChannel[Int] = (d * virtual.wMulti).map(d => math.round(d).toInt)
  }
}

sealed trait VirtualMode

object VirtualMode {
  case object Bars extends VirtualMode
  case object Clip extends VirtualMode
  case object Stretch extends VirtualMode
}

class VirtualSize(screen: VirtualSizeSupport) {
  private def c = screen

  private val _xOffset = Sub(0.0)
  private val _yOffset = Sub(0.0)
  private val _wMulti = Sub(0.0)
  private val _hMulti = Sub(0.0)
  val xOffset: ReadStateChannel[Double] = _xOffset
  val yOffset: ReadStateChannel[Double] = _yOffset
  val wMulti: ReadStateChannel[Double] = _wMulti
  val hMulti: ReadStateChannel[Double] = _hMulti

  private val updateFunction = (d: Double) => if (c.width.actual.get > 0.0 && c.height.actual.get > 0.0) {
    screen.virtualMode.get match {
      case VirtualMode.Bars | VirtualMode.Clip => {
        val widthRatio = c.width.actual.get / screen.virtualWidth.get
        val heightRatio = c.height.actual.get / screen.virtualHeight.get
        val ratio = screen.virtualMode.get match {
          case VirtualMode.Bars => math.min(widthRatio, heightRatio)
          case VirtualMode.Clip => math.max(widthRatio, heightRatio)
          case _ => 0.0   // Not possible
        }
        val w = screen.virtualWidth * ratio
        val h = screen.virtualHeight * ratio
        _xOffset := (c.width.actual.get - w) / 2.0
        _yOffset := (c.height.actual.get - h) / 2.0
        _wMulti := ratio
        _hMulti := ratio
      }
      case VirtualMode.Stretch => {
        _xOffset := 0.0
        _yOffset := 0.0
        _wMulti := c.width.actual / screen.virtualWidth
        _hMulti := c.height.actual / screen.virtualHeight
      }
    }
  }

  c.width.actual.attach(updateFunction)
  c.height.actual.attach(updateFunction)
  screen.virtualWidth.attach(updateFunction)
  screen.virtualHeight.attach(updateFunction)
  screen.virtualMode.attach { mode =>
    updateFunction(0.0)
  }
}