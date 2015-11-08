package is.launaskil.slick

import com.github.tminglei.slickpg._

trait Driver extends ExPostgresDriver with PgArraySupport {
  def pgjson = "jsonb" // jsonb support is in postgres 9.4.0 onward; for 9.3.x use "json"

  override val api = DriverApi

  object DriverApi extends API
  with ArrayImplicits {

    implicit val strListTypeMapper = new SimpleArrayJdbcType[String]("text").to(_.toList)
  }
}

object Driver extends Driver
