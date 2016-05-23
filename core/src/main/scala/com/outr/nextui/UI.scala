package com.outr.nextui

import pl.metastack.metarx.{Buffer, Sub}

trait UI {
  val currentScene: Sub[Option[Scene]] = Sub(None)

  val title: Sub[String] = Sub("")

  // case class Tree[A](h: A, t: List[Tree[A]]) { def flat: List[A] = h :: t.flatMap(_.flat) }
  // or iterator?
}