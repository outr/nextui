package com.outr.nextui

import org.powerscala.collection.HierarchicalIterator
import pl.metastack.metarx.Sub

trait UI extends Iterable[Component] {
  val currentScene: Sub[Option[Scene]] = Sub(None)

  val title: Sub[String] = Sub("")

  def iterator: Iterator[Component] = new HierarchicalIterator[Component](currentScene.get.get, {
    case container: Container[_] => container.children.iterator
    case _ => Iterator.empty
  })
}