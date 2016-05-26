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

    val group = new Pane
    group.setTranslateX(50.0)
    group.setTranslateY(50.0)

    val scene = new Scene(root, 300, 250)

    val btn = new Button
    btn.setText("Say 'Hello World'")
    btn.setOnAction(new EventHandler[ActionEvent] {
      override def handle(event: ActionEvent): Unit = {
        println("Hello World!")
        println(s"Offset: ${scene.getX}x${scene.getY}")
        primaryStage.setWidth(800 + scene.getX)
        primaryStage.setHeight(600 + scene.getY)
      }
    })
    group.getChildren.add(btn)
    root.getChildren.add(group)

//    val img = new ImageView(getClass.getClassLoader.getResource("tucker.jpg").toString)
//    root.getChildren.add(img)

    primaryStage.setScene(scene)
    primaryStage.show()
  }
}