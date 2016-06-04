package com.outr.nextui.desktop

import com.outr.nextui.{Component, Container}
import pl.metastack.metarx.Buffer.Delta.{Insert, Remove, Replace}
import pl.metastack.metarx.Buffer.Position.{After, Before, Head, Last}

trait JavaFXContainer extends JavaFXComponent {
  override val impl: javafx.scene.layout.Pane = new javafx.scene.layout.Pane

  override def initialize(): Unit = {
    super.initialize()

    component.asInstanceOf[Container].children.changes.attach {
      case insert: Insert[Component] => {
        val index = insert.position match {
          case head: Head[Component] => 0
          case last: Last[Component] => impl.getChildren.size()
          case before: Before[Component] => impl.getChildren.indexOf(before.reference.peer.asInstanceOf[JavaFXComponent].impl)
          case after: After[Component] => impl.getChildren.indexOf(after.reference.peer.asInstanceOf[JavaFXComponent].impl) + 1
        }
        insert.element.peer.init()
        impl.getChildren.add(index, insert.element.peer.asInstanceOf[JavaFXComponent].impl)
      }
      case replace: Replace[Component] => {
        val index = impl.getChildren.indexOf(replace.reference.peer.asInstanceOf[JavaFXComponent].impl)
        impl.getChildren.remove(replace.reference.peer.asInstanceOf[JavaFXComponent].impl)
        replace.element.peer.init()
        impl.getChildren.add(index, replace.element.peer.asInstanceOf[JavaFXComponent].impl)
      }
      case remove: Remove[Component] => impl.getChildren.remove(remove.element.peer.asInstanceOf[JavaFXComponent].impl)
    }
  }
}

object JavaFXContainer {
  def apply(container: Container): JavaFXContainer = new JavaFXContainer {
    override val component: Component = container
  }
}