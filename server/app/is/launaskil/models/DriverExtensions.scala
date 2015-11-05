package is.launaskil.models

import com.github.olafurpg.slick.PostgresDriver

trait DriverExtensions {
  val profile: PostgresDriver
  import profile.api._
  implicit val dateTimeMapper = MappedColumnType.base[Epoch, java.sql.Timestamp](
    { epoch => new java.sql.Timestamp(epoch.millis) },
    { ts => Epoch(ts.getTime()) }
  )

}
