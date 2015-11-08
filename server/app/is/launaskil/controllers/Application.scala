package is.launaskil.controllers

import javax.inject.Inject

import is.launaskil.slick.Driver
import com.github.olafurpg.util.HttpError
import com.mohiva.play.silhouette.api.Identity
import is.launaskil.api.BasicApi
import is.launaskil.api.BasicApiImpl
import is.launaskil.models.SchemaVersionRow
import is.launaskil.models.Tables
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import play.api.mvc.Action
import play.api.mvc.Controller
import upickle.default.Reader
import upickle.default.Writer
import scala.concurrent.ExecutionContext.Implicits.global
import autowire.Core.Request

import scala.concurrent.Future

class RPC @Inject()(val basicApi: BasicApi) extends autowire.Server[String, Reader, Writer] {
  def write[AutowireResult: Writer](r: AutowireResult) = upickle.default.write(r)
  def read[AutowireResult: Reader](p: String) = upickle.default.read[AutowireResult](p)

  val basicApiRoute = route[BasicApi](basicApi)
  val routes = basicApiRoute
}

class Application @Inject()(val dbConfigProvider: DatabaseConfigProvider, basicApi: BasicApi) extends Controller with HasDatabaseConfigProvider[Driver] with Tables {

  import driver.api._

  def index = Action.async { implicit request =>
    val q = SchemaVersionTable
    db.run(q.result).map { result =>
      println(result)
      Ok(is.launaskil.views.html.main())
    }
  }

  def api(path: String) = Action.async { implicit request =>
    try {
      val req = upickle.default.read[Request[String]](request.body.asText.get)
      val result = new RPC(basicApi).routes(req)
      result.map { txt =>
        Ok(txt)
      }
    } catch {
      case HttpError(s) => Future.successful(new Status(s))
    }
  }
}
