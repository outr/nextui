package com.outr.nextui.desktop

import java.net.URL
import java.util.concurrent.atomic.AtomicBoolean
import javafx.animation.{Animation, KeyFrame, Timeline}
import javafx.application.Application
import javafx.beans.property.ReadOnlyDoubleProperty
import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.event.{ActionEvent, EventHandler}
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.util.Duration

import com.outr.nextui._
import com.outr.nextui.model.{Image, ImagePeer, Resource, ResourcePeer}
import com.outr.scribe.Logging

import scala.language.{implicitConversions, postfixOps}

trait JavaFX extends JavaFXContainer with UIImplementation with Logging {
  this: UI =>

  override val component: Component = this

  implicit def url2String(url: URL): String = url.toString
  implicit def resource2Image(resource: Resource): Image = Image(resource.url)

  def classLoader(path: String): Resource = Resource(getClass.getClassLoader.getResource(path).toString)

  def main(args: Array[String]): Unit = {
    logger.info("Starting JavaFX...")
    JavaFXApplication.prepare(this)
    Application.launch(classOf[JavaFXApplication])
  }

  def initialize(primaryStage: Stage, application: JavaFXApplication): Unit = {
    primaryStage.setTitle(title.get)
    allChildren.map(_.peer).foreach {
      case jfx: JavaFXComponent => jfx.init()
      case c => throw new RuntimeException(s"Component peer is not a JavaFXComponent: $c.")
    }
    val scene = if (width.pref.get.nonEmpty && height.pref.get.nonEmpty) {
      new Scene(impl, width.pref.get.get, height.pref.get.get)
    } else {
      new Scene(impl)
    }
    primaryStage.setScene(scene)

    prefBind(width, primaryStage.setWidth, primaryStage.widthProperty(), scene.getX * 2.0)
    prefBind(height, primaryStage.setHeight, primaryStage.heightProperty(), scene.getY)

    val delay = math.round((1.0 / updateFPS) * 1000.0)
    val timeline = new Timeline(new KeyFrame(Duration.millis(delay), new EventHandler[ActionEvent] {
      var previous = System.currentTimeMillis()

      override def handle(event: ActionEvent): Unit = {
        val current = System.currentTimeMillis()
        val delta = (current - previous) / 1000.0
        update(delta)
        previous = current
      }
    }))
    timeline.setCycleCount(Animation.INDEFINITE)
    timeline.play()

    primaryStage.show()
  }

  protected def prefBind(size: SizeElement,
                         setter: Double => Unit,
                         prop: ReadOnlyDoubleProperty,
                         adjust: => Double = 0.0): Unit = {
    val changing = new AtomicBoolean(false)

    size.pref.silentAttach {
      case Some(d) if !changing.get() => setter(d + adjust)
      case _ => // Ignore
    }

    prop.addListener(new ChangeListener[Number] {
      override def changed(observable: ObservableValue[_ <: Number], oldValue: Number, newValue: Number): Unit = {
        val value = newValue.doubleValue() - adjust
        if (value != size.actual.get) {
          changing.set(true)
          try {
            size._actual := value
          } finally {
            changing.set(false)
          }
        }
      }
    })
  }

  override def peerFor(component: Component): Option[Peer[_]] = component match {
    case b: Button => Some(new JavaFXButton(b))
    case i: ImageView => Some(new JavaFXImageView(i))
    case fx: JavaFX => Some(fx)
    case c: Container => Some(JavaFXContainer(c))
    case _ => None
  }
  override def peerFor(resource: Resource): ResourcePeer = new JavaFXResource(resource)
  override def peerFor(image: Image): ImagePeer = new javafx.scene.image.Image(image.url.toString) with ImagePeer
}

class JavaFXResource(resource: Resource) extends ResourcePeer

class JavaFXApplication extends Application {
  override def start(primaryStage: Stage): Unit = {
    val ui = JavaFXApplication.use()
    ui.initialize(primaryStage, this)
  }
}

object JavaFXApplication {
  private var instance: Option[JavaFX] = None

  def use(): JavaFX = synchronized {
    instance match {
      case Some(ui) => {
        instance = None
        ui
      }
      case None => throw new RuntimeException("No UI defined for JavaFX.")
    }
  }

  def prepare(ui: JavaFX): Unit = synchronized {
    instance match {
      case Some(existing) => throw new RuntimeException(s"Cannot define multiple UIs before initialization. Already defined: $existing, Trying to define: $ui.")
      case None => instance = Some(ui)
    }
  }
}