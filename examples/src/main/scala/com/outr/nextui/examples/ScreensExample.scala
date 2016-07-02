package com.outr.nextui.examples

import com.outr.nextui.model.Resource
import com.outr.nextui.screen.{Screen, Screens}
import com.outr.nextui.transition._
import com.outr.nextui.{Button, ImageView, UI, _}
import org.powerscala.Color

object ScreensExample extends UI {
  title := "Screens Example"
  size.width := 1024.0
  size.height := 868.0

  background := Color.Azure

  MainScreen.transitionOut := Some(MainScreen.position.right transitionTo 0.0 in 2.seconds)
  OtherScreen.transitionIn := Some(function(OtherScreen.position.left := ui.size.width.actual) andThen(OtherScreen.position.left transitionTo 0.0 in 2.seconds))

  children += new Button {
    text := "Change Screens"

    position.right := ui.position.right - 50.0
    position.middle := ui.size.height.actual - 50.0

    action.attach { evt =>
      screens.active := (if (screens.active.get.contains(MainScreen)) Some(OtherScreen) else Some(MainScreen))
    }
  }

  // Set up our screens container
  val screens = new Screens
  screens.size.height := ui.size.height.actual - 100.0
  children += screens

  screens.active := Some(MainScreen)
}

object MainScreen extends Screen()(ScreensExample) {
  background := Color.CadetBlue

  children += new ImageView {
    src := Resource("sgine.png")

    position.center := MainScreen.position.center
    position.middle := MainScreen.position.middle
  }
}

object OtherScreen extends Screen()(ScreensExample) {
  children += new ImageView {
    src := Resource("1024.jpg")

    position.center := OtherScreen.position.center
    position.middle := OtherScreen.position.middle
  }
}