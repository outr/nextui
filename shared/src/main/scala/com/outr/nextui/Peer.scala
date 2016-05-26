package com.outr.nextui

trait Peer[I] {
  def component: Component
  def impl: I

  def init(): Unit
}