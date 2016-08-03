package com.outr.nextui

import pl.metastack.metarx.Buffer
import pl.metastack.metarx.Buffer.Delta._

class Container extends Component {
  val children: Buffer[Component] = Buffer[Component]

  // Maintain Parent references for all children
  children.changes.attach { c =>
    val (removed, added) = c match {
      case insert: Insert[Component] => None -> Some(insert.element)
      case replace: Replace[Component] => Some(replace.reference) -> Some(replace.element)
      case remove: Remove[Component] => Some(remove.element) -> None
    }
    val p = Some(this)
    removed.foreach(_._parent := None)
    added.foreach(_._parent := p)
  }

  override def update(delta: Double): Unit = {
    super.update(delta)

    children.foreach(_.update(delta))
  }
}
