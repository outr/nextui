package com.outr.nextui.desktop

import java.util.concurrent.atomic.AtomicBoolean
import javafx.beans.property.ReadOnlyDoubleProperty
import javafx.beans.value.{ChangeListener, ObservableValue}

import com.outr.nextui.Peer
import pl.metastack.metarx.Var

trait JavaFXComponent extends Peer[javafx.scene.layout.Region] {
  override def init(): Unit = {
    component.parent.attach {
      case Some(p) => {
        p.peer.asInstanceOf[JavaFXContainer].impl.getChildren.add(impl)
      }
      case None => // No parent
    }

    if (component.width.pref.get == 0.0) {
      component.width.pref := impl.getPrefWidth
    }
    if (component.height.pref.get == 0.0) {
      component.height.pref := impl.getPrefHeight
    }

    component.x.attach(impl.setTranslateX)
    component.y.attach(impl.setTranslateY)
    doubleBind(component.width.min, impl.setMinWidth, impl.minWidthProperty())
    doubleBind(component.width.max, impl.setMaxWidth, impl.maxWidthProperty())
    doubleBind(component.width.pref, impl.setPrefWidth, impl.prefWidthProperty())
    doubleBind(component.height.min, impl.setMinHeight, impl.minHeightProperty())
    doubleBind(component.height.max, impl.setMaxHeight, impl.maxHeightProperty())
    doubleBind(component.height.pref, impl.setPrefHeight, impl.prefHeightProperty())

    if (!isInstanceOf[JavaFX]) {
      doubleReversed(component.width._actual, impl.widthProperty())
      doubleReversed(component.height._actual, impl.heightProperty())
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
