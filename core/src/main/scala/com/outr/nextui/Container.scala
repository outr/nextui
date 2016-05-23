package com.outr.nextui

import pl.metastack.metarx.Buffer
import pl.metastack.metarx.Buffer.Delta._

trait Container[C <: Component] extends Component {
  val children: Buffer[C] = Buffer[C]

  // Maintain Parent references for all children
  children.changes.attach { c =>
    val (removed, added) = c match {
      case insert: Insert[C] => None -> Some(insert.element)
      case replace: Replace[C] => Some(replace.reference) -> Some(replace.element)
      case remove: Remove[C] => Some(remove.element) -> None
    }
    val p = Some(this)
    removed.foreach(_._parent := None)
    added.foreach(_._parent := p)
  }
}
