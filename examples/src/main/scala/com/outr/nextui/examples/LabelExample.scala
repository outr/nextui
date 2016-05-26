package com.outr.nextui.examples

import com.outr.nextui.{FontStyle, FontWeight, Label, UI}
import org.powerscala.Color

object LabelExample extends UI {
  width := 800.0
  height := 600.0

  children += new Label {
    text := "Centered Text"
    center := ui.center
    middle := ui.middle
    font.size := 24.0
    font.weight := FontWeight.Bold
    color := Color.Blue
  }

  children += new Label {
    text := "Top Left"
    left := 50.0
    top := 50.0
    font.size := 18.0
    font.style := FontStyle.Italic
    color := Color.Red
  }

  children += new Label {
    text := "Top Right"
    right := ui.right - 50.0
    top := 50.0
    font.size := 18.0
    font.style := FontStyle.Italic
    color := Color.Green
  }

  children += new Label {
    text := "Bottom Left"
    left := 50.0
    bottom := ui.bottom - 50.0
    font.size := 18.0
    color := Color.Purple
  }

  children += new Label {
    text := "Bottom Right"
    right := ui.right - 50.0
    bottom := ui.bottom - 50.0
    font.size := 18.0
    color := Color.Cyan
  }
}
