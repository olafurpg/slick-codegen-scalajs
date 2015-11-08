package is.launaskil.models

import java.util.Date

object Timestamp {
  def now: Timestamp = Timestamp(new Date().getTime)
}

case class Timestamp(millis: Long) extends java.util.Date {
  override def getTime = millis
}

