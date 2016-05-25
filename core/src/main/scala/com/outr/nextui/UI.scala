package com.outr.nextui

import com.outr.nextui.util.HierarchicalIterator
import pl.metastack.metarx.Sub

trait UI extends Container[Component] {
  val title: Sub[String] = Sub("")

  def allChildren: Iterator[Component] = new HierarchicalIterator[Component](this, {
    case container: Container[_] => container.children.iterator
    case _ => Iterator.empty
  })
}