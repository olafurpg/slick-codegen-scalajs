package is.launaskil.api

import is.launaskil.models.AppUserRow
import is.launaskil.models.SchemaVersionRow


import scala.concurrent.Future

trait BasicApi {
  def helloLaunaskil(hello: String): String
  def gimmeUsers(): Future[List[AppUserRow]]
}
