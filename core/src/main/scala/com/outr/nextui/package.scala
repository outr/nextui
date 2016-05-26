package com.outr

package object nextui {
  implicit class IntTimes(i: Int) {
    def millis: Double = i.toDouble / 1000.0
    def milliseconds: Double = i.toDouble / 1000.0
    def seconds: Double = i.toDouble
    def minutes: Double = i.toDouble * 60.0
    def hours: Double = i.toDouble * 60.0 * 60.0
    def days: Double = i.toDouble * 60.0 * 60.0 * 24.0
  }

  implicit class DoubleTimes(d: Double) {
    def millis: Double = d / 1000.0
    def milliseconds: Double = d / 1000.0
    def seconds: Double = d
    def minutes: Double = d * 60.0
    def hours: Double = d * 60.0 * 60.0
    def days: Double = d * 60.0 * 60.0 * 24.0
    def toMillis: Long = math.round(d * 1000.0)
  }
}