package com.outr.nextui.browser

import com.outr.nextui.{Component, Container}
import org.scalajs.dom.raw.HTMLDivElement
import pl.metastack.metarx.Buffer.Delta.{Insert, Remove, Replace}
import pl.metastack.metarx.Buffer.Position.{After, Before, Head, Last}

trait ScalaJSContainer extends ScalaJSComponent {
  override val impl: HTMLDivElement = create[HTMLDivElement]("div")

  override def initialize(): Unit = {
    super.initialize()

    component.asInstanceOf[Container].children.changes.attach {
      case insert: Insert[Component] => {
        val e = insert.element.peer.asInstanceOf[ScalaJSComponent].impl
        insert.position match {
          case _ if !impl.hasChildNodes() => impl.appendChild(e)
          case head: Head[Component] => impl.insertBefore(impl.childNodes(0), e)
          case last: Last[Component] => impl.appendChild(e)
          case before: Before[Component] => impl.insertBefore(before.reference.peer.asInstanceOf[ScalaJSComponent].impl, e)
          case after: After[Component] => impl.insertBefore(after.reference.peer.asInstanceOf[ScalaJSComponent].impl.nextElementSibling, e)
        }
        insert.element.peer.init()
      }
      case replace: Replace[Component] => {
        val e = replace.element.peer.asInstanceOf[ScalaJSComponent].impl
        impl.replaceChild(e, replace.reference.peer.asInstanceOf[ScalaJSComponent].impl)
        replace.element.peer.init()
      }
      case remove: Remove[Component] => impl.parentElement.removeChild(impl)
    }
  }
}

object ScalaJSContainer {
  def apply(container: Container): ScalaJSContainer = new ScalaJSContainer {
    override val component: Component = container
  }
}