package com.outr.nextui

import org.powerscala.collection.HierarchicalIterator
import pl.metastack.metarx.Sub

trait UI extends Container {
  val title: Sub[String] = Sub("")
  def ui: UI = this

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
}