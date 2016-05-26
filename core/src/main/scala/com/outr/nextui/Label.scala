package com.outr.nextui

import pl.metastack.metarx.Sub

class Label extends Component {
  val text: Sub[String] = Sub("")

  object font {
    val family: Sub[String] = Sub("Sans-Serif")
    val weight: Sub[FontWeight] = Sub(FontWeight.Normal)
    val style: Sub[FontStyle] = Sub(FontStyle.Regular)
    val size: Sub[Double] = Sub(14.0)
  }
}

case class FontWeight(name: String)

object FontWeight {
  val Thin = FontWeight("Thin")
  val ExtraLight = FontWeight("Extra Light")
  val Light = FontWeight("Light")
  val Normal = FontWeight("Normal")
  val Medium = FontWeight("Medium")
  val SemiBold = FontWeight("Semi Bold")
  val Bold = FontWeight("Bold")
  val ExtraBold = FontWeight("Extra Bold")
}

case class FontStyle(name: String)

object FontStyle {
  val Regular = FontStyle("Regular")
  val Italic = FontStyle("Italic")
}