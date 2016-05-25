package com.outr.nextui.desktop

import com.outr.nextui.{Component, Peer}

trait JavaFXComponent extends javafx.scene.Node with Peer {
  def component: Component

  def init(): Unit = {
    component.parent.attach {
      case Some(p) => {
        p.peer.asInstanceOf[JavaFXContainer].getChildren.add(this)
      }
      case None => // No parent
    }
  }
}
