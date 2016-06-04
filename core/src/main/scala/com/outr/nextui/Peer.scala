package com.outr.nextui

trait Peer[I] {
  val component: Component
  val impl: I

  private var _initialized = false
  final def initialized: Boolean = _initialized

  final def init(): Unit = synchronized {
    if (!initialized) {
      initialize()
      _initialized = true
    }
  }
  protected def initialize(): Unit
}