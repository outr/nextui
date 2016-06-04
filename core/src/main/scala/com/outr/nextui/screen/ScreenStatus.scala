package com.outr.nextui.screen

sealed trait ScreenStatus

/**
  * ScreenStatus represents the current status of a Screen.
  */
object ScreenStatus {
  /**
    * A Screen is `Removed` when it is not currently attached and visible on a Screens instance.
    */
  case object Removed extends ScreenStatus

  /**
    * A Screen is `TransitioningIn` when it has been activated and is transitioning onto the screen but the transition
    * is still active.
    */
  case object TransitioningIn extends ScreenStatus

  /**
    * A Screen is `TransitioningOut` when it has been replaced from active status and is transitioning off the screen
    * but has not finished.
    */
  case object TransitioningOut extends ScreenStatus

  /**
    * A Screen is `Active` when it has been activated onto a Screens instance and the transitioning in stage has fully
    * completed.
    */
  case object Active extends ScreenStatus
}