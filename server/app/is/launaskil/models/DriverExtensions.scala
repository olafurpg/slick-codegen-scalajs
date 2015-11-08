package is.launaskil.models

import is.launaskil.slick.Driver


trait DriverExtensions {
  val profile: Driver
  import profile.api._
  implicit val dateTimeMapper = MappedColumnType.base[Timestamp, java.sql.Timestamp](
    { epoch => new java.sql.Timestamp(epoch.millis) },
    { ts => Timestamp(ts.getTime) }
  )
  implicit val utilDateMapper = MappedColumnType.base[java.util.Date, java.sql.Timestamp](
    { date => new java.sql.Timestamp(date.getTime) },
    { ts => ts.asInstanceOf[java.util.Date] }
  )

}
