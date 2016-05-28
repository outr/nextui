package com.outr.nextui.desktop

import java.util.concurrent.atomic.AtomicBoolean
import javafx.beans.property.ReadOnlyDoubleProperty
import javafx.beans.value.{ChangeListener, ObservableValue}

import com.outr.nextui.Peer
import pl.metastack.metarx.Var

trait JavaFXComponent extends Peer[javafx.scene.Node] {
  override def init(): Unit = {
    component.parent.attach {
      case Some(p) => {
        p.peer.asInstanceOf[JavaFXContainer].impl.getChildren.add(impl)
      }
      case None => // No parent
    }

    component.x.attach(impl.setTranslateX)
    component.y.attach(impl.setTranslateY)
    impl match {
      case region: javafx.scene.layout.Region => {
        if (component.width.pref.get == 0.0) {
          component.width.pref := region.getPrefWidth
        }
        if (component.height.pref.get == 0.0) {
          component.height.pref := region.getPrefHeight
        }
        doubleBind(component.width.min, region.setMinWidth, region.minWidthProperty())
        doubleBind(component.width.max, region.setMaxWidth, region.maxWidthProperty())
        doubleBind(component.width.pref, region.setPrefWidth, region.prefWidthProperty())
        doubleBind(component.height.min, region.setMinHeight, region.minHeightProperty())
        doubleBind(component.height.max, region.setMaxHeight, region.maxHeightProperty())
        doubleBind(component.height.pref, region.setPrefHeight, region.prefHeightProperty())

        if (!isInstanceOf[JavaFX]) {
          doubleReversed(component.width._actual, region.widthProperty())
          doubleReversed(component.height._actual, region.heightProperty())
        }
      }
      case _ => // Not a region
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
