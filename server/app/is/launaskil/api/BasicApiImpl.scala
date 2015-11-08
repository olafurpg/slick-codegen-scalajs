package is.launaskil.api

import is.launaskil.models.Timestamp
import is.launaskil.slick.Driver
import com.google.inject.Inject
import is.launaskil.models.AppUserRow
import is.launaskil.models.SchemaVersionRow
import is.launaskil.models.Tables
import org.joda.time.DateTime
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

class BasicApiImpl @Inject()(val dbConfigProvider: DatabaseConfigProvider) extends BasicApi with HasDatabaseConfigProvider[Driver] with Tables {
  import driver.api._

  def helloLaunaskil(hello: String) = hello + " Launaskil!"
  def gimmeUsers(): Future[List[AppUserRow]] =
    Future.successful(List(AppUserRow(1, Some(Timestamp.now), None, List("a"))))
}
