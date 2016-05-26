package com.outr.nextui.desktop

import com.outr.nextui.{Component, Peer}

trait JavaFXComponent extends Peer {
  def component: Component
  def node: javafx.scene.Node

  def init(): Unit = {
    component.parent.attach {
      case Some(p) => {
        p.peer.asInstanceOf[JavaFXContainer].node.getChildren.add(node)
      }
      case None => // No parent
    }
  }
}
