package com.outr.nextui.desktop

import java.util.concurrent.atomic.AtomicBoolean
import javafx.beans.property.ReadOnlyDoubleProperty
import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.scene.layout.{Background, BackgroundFill}

import com.outr.nextui.Peer
import org.powerscala.Color
import pl.metastack.metarx._

trait JavaFXComponent extends Peer[javafx.scene.Node] {
  override def initialize(): Unit = {
    component.visible.attach(impl.setVisible)
    component.x.attach(impl.setTranslateX)
    component.y.attach(impl.setTranslateY)
    doubleBind(component.scale.x, impl.setScaleX, impl.scaleXProperty())
    doubleBind(component.scale.y, impl.setScaleY, impl.scaleYProperty())
    component.rotation.attach(impl.setRotate)
    component.opacity.attach(impl.setOpacity)
    impl match {
      case region: javafx.scene.layout.Region => {
        if (component.width.pref.get.isEmpty && region.getPrefWidth > 0.0) {
          component.width.pref := Some(region.getPrefWidth)
        }
        if (component.height.pref.get.isEmpty && region.getPrefHeight > 0.0) {
          component.height.pref := Some(region.getPrefHeight)
        }
        doubleBind(component.width.min, region.setMinWidth, region.minWidthProperty())
        doubleBind(component.width.max, region.setMaxWidth, region.maxWidthProperty())
        doubleBind(component.height.min, region.setMinHeight, region.minHeightProperty())
        doubleBind(component.height.max, region.setMaxHeight, region.maxHeightProperty())

        component.background.attach { c =>
          if (c != Color.Clear) {
            val fill = new BackgroundFill(new javafx.scene.paint.Color(c.red, c.green, c.blue, c.alpha), null, null)
            region.setBackground(new Background(fill))
          } else if (region.getBackground != null) {
            region.setBackground(null)
          }
        }

        if (!isInstanceOf[JavaFX]) {
          doubleBindOption(component.width.pref, region.setPrefWidth, region.prefWidthProperty())
          doubleBindOption(component.height.pref, region.setPrefHeight, region.prefHeightProperty())
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

  protected def doubleBindOption(store: Var[Option[Double]],
                           setter: Double => Unit,
                           prop: ReadOnlyDoubleProperty,
                           adjust: => Double = 0.0): Unit = {
    val changing = new AtomicBoolean(false)

    store.attach {
      case Some(d) if !changing.get() => setter(d + adjust)
      case _ => // Ignore
    }
    prop.addListener(new ChangeListener[Number] {
      override def changed(observable: ObservableValue[_ <: Number], oldValue: Number, newValue: Number): Unit = {
        val value = newValue.doubleValue() - adjust
        if (value != store.get.getOrElse(0.0)) {
          changing.set(true)
          try {
            store := Some(value)
          } finally {
            changing.set(false)
          }
        }
      }
    })
  }
}
