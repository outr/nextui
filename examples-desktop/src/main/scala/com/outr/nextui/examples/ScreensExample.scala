package com.outr.nextui.examples

import com.outr.nextui.desktop._
import com.outr.nextui.screen.{Screen, Screens}
import com.outr.nextui.transition._
import com.outr.nextui.{Button, ImageView, UI, _}
import org.powerscala.Color

object ScreensExample extends UI with JavaFX {
  title := "Screens Example"
  width := 1024.0
  height := 868.0

  background := Color.Azure

  MainScreen.transitionOut := Some(MainScreen.right transitionTo 0.0 in 2.seconds)
  OtherScreen.transitionIn := Some(function(OtherScreen.left := ui.width.actual) andThen(OtherScreen.left transitionTo 0.0 in 2.seconds))

  children += new Button {
    text := "Change Screens"

    right := ui.right - 50.0
    middle := ui.height.actual - 50.0

    action.attach { evt =>
      screens.active := (if (screens.active.get.contains(MainScreen)) Some(OtherScreen) else Some(MainScreen))
    }
  }

  // Set up our screens container
  val screens = new Screens
  screens.height := ui.height.actual - 100.0
  children += screens

  screens.active := Some(MainScreen)
}

object MainScreen extends Screen {
  background := Color.CadetBlue

  children += new ImageView {
    src := classLoader("sgine.png")

    center := MainScreen.center
    middle := MainScreen.middle
  }
}

object OtherScreen extends Screen {
  children += new ImageView {
    src := classLoader("1024.jpg")

    center := OtherScreen.center
    middle := OtherScreen.middle
  }
}