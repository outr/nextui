package com.outr.nextui

import com.outr.nextui.util.HierarchicalIterator
import pl.metastack.metarx.Sub

trait UI extends Container {
  val title: Sub[String] = Sub("")

  def allChildren: Iterator[Component] = new HierarchicalIterator[Component](this, {
    case container: Container => container.children.iterator
    case _ => Iterator.empty
  })
}