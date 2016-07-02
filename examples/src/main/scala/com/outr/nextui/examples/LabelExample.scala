package com.outr.nextui.examples

import com.outr.nextui.{FontStyle, FontWeight, Label, UI}
import org.powerscala.Color

object LabelExample extends UI {
  size.width := 800.0
  size.height := 600.0

  children += new Label {
    text := "Centered Text"
    position.center := ui.position.center
    position.middle := ui.position.middle
    font.size := 24.0
    font.weight := FontWeight.Bold
    color := Color.Blue
  }

  children += new Label {
    text := "Top Left"
    position.left := 50.0
    position.top := 50.0
    font.size := 18.0
    font.style := FontStyle.Italic
    color := Color.Red
  }

  children += new Label {
    text := "Top Right"
    position.right := ui.position.right - 50.0
    position.top := 50.0
    font.size := 18.0
    font.style := FontStyle.Italic
    color := Color.Green
  }

  children += new Label {
    text := "Bottom Left"
    position.left := 50.0
    position.bottom := ui.position.bottom - 50.0
    font.size := 18.0
    color := Color.Purple
  }

  children += new Label {
    text := "Bottom Right"
    position.right := ui.position.right - 50.0
    position.bottom := ui.position.bottom - 50.0
    font.size := 18.0
    color := Color.Cyan
  }
}
