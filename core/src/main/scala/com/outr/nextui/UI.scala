package com.outr.nextui

import org.powerscala.collection.HierarchicalIterator
import pl.metastack.metarx.{ReadChannel, Sub}

trait UI extends Container {
  val title: Sub[String] = Sub("")
  val fullScreen: Sub[Boolean] = Sub(false)
  val fullScreenExitHint: Sub[Option[String]] = Sub(None)
  def ui: UI = this

  UI.instance = Some(this)

  /**
    * The rate at which the update method is called. How this is managed is implementation specific.
    */
  val updateFPS: Int = 60
  /**
    * Supports injecting actions into the update queue.
    */
  val updates = new ActionManager("updates")

  /**
    * Updates via the implementation at the rate defined in `updateFPS`.
    *
    * @param delta the time delay in seconds since the last update.
    */
  def update(delta: Double): Unit = {
    updates.exec(delta)
  }

  def allChildren: Iterator[Component] = new HierarchicalIterator[Component](this, {
    case container: Container => container.children.iterator
    case _ => Iterator.empty
  })

  implicit class IntSize(i: Int) {
    def px: Double = i.toDouble
    def pctw: ReadChannel[Double] = (i.toDouble / 100.0) * width.actual
    def pcth: ReadChannel[Double] = (i.toDouble / 100.0) * height.actual
    def pct: Percent = Percent(i.toDouble)
  }
  implicit class DoubleSize(d: Double) {
    def px: Double = d
    def pctw: ReadChannel[Double] = (d / 100.0) * width.actual
    def pcth: ReadChannel[Double] = (d / 100.0) * height.actual
    def pct: Percent = Percent(d)
  }
}

object UI {
  private var instance: Option[UI] = None

  def apply(): UI = instance.get

  def get(): Option[UI] = instance
}

case class Percent(pct: Double) {
  def of(rc: ReadChannel[Double]): ReadChannel[Double] = rc * (pct / 100.0)
  def of(other: Double): Double = other * (pct / 100.0)
}