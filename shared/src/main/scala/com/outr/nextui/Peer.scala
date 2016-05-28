package com.outr.nextui

trait Peer[I] {
  val component: Component
  val impl: I

  def init(): Unit
}