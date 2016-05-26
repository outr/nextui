package com.outr.nextui.examples.browser

import com.outr.nextui.browser.ScalaJS
import com.outr.nextui.examples.Examples
import org.scalajs.dom.{Event, document, html}

import scala.scalajs.js.JSApp

object ScalaJSExamples extends JSApp {
  override def main(): Unit = {
    val div = document.createElement("div").asInstanceOf[html.Div]
    Examples().foreach { ui =>
      val a = document.createElement("a").asInstanceOf[html.Anchor]
      a.href = "#"
      a.addEventListener("click", {(evt: Event) =>
        document.body.removeChild(div)
        ScalaJS(ui)
      }, true)
      a.textContent = ui.name
      val p = document.createElement("p").asInstanceOf[html.Paragraph]
      p.appendChild(a)
      div.appendChild(p)
    }
    document.body.appendChild(div)
  }
}
