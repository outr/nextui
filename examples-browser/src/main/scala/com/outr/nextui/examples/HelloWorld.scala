package com.outr.nextui.examples

import org.scalajs.dom
import org.scalajs.dom.document

import scala.scalajs.js.JSApp

object HelloWorld extends JSApp {
  def main(): Unit = {
    val button = document.createElement("button")
    button.innerHTML = "Hello World!"
    document.body.appendChild(button)
  }
}
