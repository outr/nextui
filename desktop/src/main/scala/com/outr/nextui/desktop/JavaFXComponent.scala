package com.outr.nextui.desktop

import java.util.concurrent.atomic.AtomicBoolean
import javafx.beans.property.ReadOnlyDoubleProperty
import javafx.beans.value.{ChangeListener, ObservableValue}

import com.outr.nextui.{Component, Peer}
import pl.metastack.metarx.Var

trait JavaFXComponent extends Peer {
  def component: Component
  def node: javafx.scene.layout.Region

  def init(): Unit = {
    component.parent.attach {
      case Some(p) => {
        p.peer.asInstanceOf[JavaFXContainer].node.getChildren.add(node)
      }
      case None => // No parent
    }

    if (component.width.pref.get == 0.0) {
      component.width.pref := node.getPrefWidth
    }
    if (component.height.pref.get == 0.0) {
      component.height.pref := node.getPrefHeight
    }

    component.x.attach(node.setTranslateX)
    component.y.attach(node.setTranslateY)
    doubleBind(component.width.min, node.setMinWidth, node.minWidthProperty())
    doubleBind(component.width.max, node.setMaxWidth, node.maxWidthProperty())
    doubleBind(component.width.pref, node.setPrefWidth, node.prefWidthProperty())
    doubleBind(component.height.min, node.setMinHeight, node.minHeightProperty())
    doubleBind(component.height.max, node.setMaxHeight, node.maxHeightProperty())
    doubleBind(component.height.pref, node.setPrefHeight, node.prefHeightProperty())

    if (!isInstanceOf[JavaFX]) {
      doubleReversed(component.width._actual, node.widthProperty())
      doubleReversed(component.height._actual, node.heightProperty())
    }
  }

  protected def doubleReversed(store: Var[Double], prop: ReadOnlyDoubleProperty): Unit = {
    prop.addListener(new ChangeListener[Number] {
      override def changed(observable: ObservableValue[_ <: Number], oldValue: Number, newValue: Number): Unit = {
        val value = newValue.doubleValue()
        store := value
      }
    })
  }

  protected def doubleBind(store: Var[Double],
                           setter: Double => Unit,
                           prop: ReadOnlyDoubleProperty,
                           adjust: => Double = 0.0,
                           silent: Boolean = false): Unit = {
    val changing = new AtomicBoolean(false)

    if (silent) {
      store.silentAttach { d =>
        if (!changing.get()) {
          setter(d + adjust)
        }
      }
    } else {
      store.attach { d =>
        if (!changing.get()) {
          setter(d + adjust)
        }
      }
    }
    prop.addListener(new ChangeListener[Number] {
      override def changed(observable: ObservableValue[_ <: Number], oldValue: Number, newValue: Number): Unit = {
        val value = newValue.doubleValue() - adjust
        if (value != store.get) {
          changing.set(true)
          try {
            store := value
          } finally {
            changing.set(false)
          }
        }
      }
    })
  }
}
