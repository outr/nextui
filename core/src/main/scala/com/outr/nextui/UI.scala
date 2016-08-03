package com.outr.nextui

import com.outr.scribe.Logging
import org.powerscala.collection.HierarchicalIterator
import pl.metastack.metarx.{Channel, ReadChannel, Sub}

trait UI extends Container with Logging {
  Channel.ValidateCyclical = false

  lazy val name: String = getClass.getSimpleName.replaceAll("[$]", "")
  val title: Sub[String] = Sub(name)
  val fullScreen: Sub[Boolean] = Sub(false)
  val fullScreenExitHint: Sub[Option[String]] = Sub(None)
  implicit def ui: UI = this

  /**
    * The rate at which the update method is called. How this is managed is implementation specific.
    */
  val updateFPS: Int = 60

  /**
    * Initialization called by implementation when ready to show.
    */
  def init(): Unit = {
  }

  def allChildren: Iterator[Component] = new HierarchicalIterator[Component](this, {
    case container: Container => container.children.iterator
    case _ => Iterator.empty
  })

  implicit class IntSize(i: Int) {
    def px: Double = i.toDouble
    def pctw: ReadChannel[Double] = (i.toDouble / 100.0) * size.width.actual
    def pcth: ReadChannel[Double] = (i.toDouble / 100.0) * size.height.actual
    def pct: Percent = Percent(i.toDouble)
  }
  implicit class DoubleSize(d: Double) {
    def px: Double = d
    def pctw: ReadChannel[Double] = (d / 100.0) * size.width.actual
    def pcth: ReadChannel[Double] = (d / 100.0) * size.height.actual
    def pct: Percent = Percent(d)
  }
}

case class Percent(pct: Double) {
  def of(rc: ReadChannel[Double]): ReadChannel[Double] = rc * (pct / 100.0)
  def of(other: Double): Double = other * (pct / 100.0)
}