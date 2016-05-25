package com.outr.nextui.desktop

import com.outr.nextui.Component

trait JavaFXComponent {
  this: Component =>

  def peer: javafx.scene.Node

  def init(): Unit = {
    parent.attach {
      case Some(p) => {
        val container = p.asInstanceOf[JavaFXContainer[Component]]
        container.peer.getChildren.add(peer)
      }
      case None => // No parent
    }
  }
}
