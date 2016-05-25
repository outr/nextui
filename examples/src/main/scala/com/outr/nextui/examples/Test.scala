package com.outr.nextui.examples

import javafx.application.Application
import javafx.event.{ActionEvent, EventHandler}
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.Pane
import javafx.stage.Stage

object Test {
  def main(args: Array[String]): Unit = {
    Application.launch(classOf[Test])
  }
}

class Test extends Application {
  override def start(primaryStage: Stage): Unit = {
    primaryStage.setTitle("Hello World!")

    val root = new Pane
//    root.setAlignment(Pos.BASELINE_LEFT)

    val btn = new Button
    btn.setTranslateX(50.0)
    btn.setText("Say 'Hello World'")
    btn.setOnAction(new EventHandler[ActionEvent] {
      override def handle(event: ActionEvent): Unit = println("Hello World!")
    })
    root.getChildren.add(btn)

//    val img = new ImageView(getClass.getClassLoader.getResource("tucker.jpg").toString)
//    root.getChildren.add(img)

    primaryStage.setScene(new Scene(root, 300, 250))
    primaryStage.show()
  }
}