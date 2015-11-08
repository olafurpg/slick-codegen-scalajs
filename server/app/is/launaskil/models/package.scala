package is.launaskil

import is.launaskil.models.Timestamp$
import org.joda.time.DateTime
import scala.language.implicitConversions


package object models {
  implicit def time2joda(time: Timestamp): DateTime = new DateTime(time.millis)
}
