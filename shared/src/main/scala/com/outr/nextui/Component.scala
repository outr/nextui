package com.outr.nextui

import pl.metastack.metarx._

trait Component {
  lazy val peer: Peer[_] = UIImplementation.peerFor(this)

  protected[nextui] val _parent = Var[Option[Container]](None)

  def parent: ReadStateChannel[Option[Container]] = _parent

  val x: Sub[Double] = Sub(0.0)
  val y: Sub[Double] = Sub(0.0)
  val width: SizeElement = new SizeElement
  val height: SizeElement = new SizeElement

  def left: Sub[Double] = x
  lazy val center: Dep[Double, Double] = x.dep(_ + (width.actual / 2.0), _ - (width.actual / 2.0))
  lazy val right: Dep[Double, Double] = x.dep(_ + width.actual, _ - width.actual)

  def top: Sub[Double] = y
  lazy val middle: Dep[Double, Double] = y.dep(_ + (height.actual / 2.0), _ - (height.actual / 2.0))
  lazy val bottom: Dep[Double, Double] = y.dep(_ + height.actual, _ - height.actual)
}

class SizeElement {
  private[nextui] val _actual = Var[Double](0.0)

  def actual: ReadStateChannel[Double] = _actual
  val min: Sub[Double] = Sub(0.0)
  val max: Sub[Double] = Sub(Double.MaxValue)
  val pref: Sub[Double] = Sub(0.0)
}