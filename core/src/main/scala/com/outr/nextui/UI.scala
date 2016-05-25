package com.outr.nextui

import org.powerscala.collection.HierarchicalIterator
import pl.metastack.metarx.Sub

trait UI extends Iterable[Component] {
  val currentScreen: Sub[Option[Screen]] = Sub(None)

  val title: Sub[String] = Sub("")

  def iterator: Iterator[Component] = new HierarchicalIterator[Component](currentScreen.get.get, {
    case container: Container[_] => container.children.iterator
    case _ => Iterator.empty
  })
}