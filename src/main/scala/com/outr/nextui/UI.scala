package com.outr.nextui

trait UI {
  object scene {
    def +=[C <: Component](component: C): C = component
  }

  def main(args: Array[String]): Unit = {
    println(s"UI! ${getClass}")
  }
}
