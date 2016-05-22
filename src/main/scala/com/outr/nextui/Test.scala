package test

import javafx.application.Application
import javafx.event.{ActionEvent, EventHandler}
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.StackPane
import javafx.stage.Stage

object Test {
  def main(args: Array[String]): Unit = {
    Application.launch(classOf[Test])
  }
}

class Test extends Application {
  override def start(primaryStage: Stage): Unit = {
    primaryStage.setTitle("Hello World!")

    val btn = new Button
    btn.setText("Say 'Hello World'")
    btn.setOnAction(new EventHandler[ActionEvent] {
      override def handle(event: ActionEvent): Unit = println("Hello World!")
    })

    val root = new StackPane
    root.getChildren.add(btn)
    primaryStage.setScene(new Scene(root, 300, 250))
    primaryStage.show()
  }
}