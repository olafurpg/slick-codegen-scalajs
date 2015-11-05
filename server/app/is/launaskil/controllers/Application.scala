package is.launaskil.controllers

import javax.inject.Inject

import com.github.olafurpg.util.HttpError
import com.mohiva.play.silhouette.api.Identity
import is.launaskil.api.BasicApi
import is.launaskil.api.BasicApiImpl
import play.api.mvc.Action
import play.api.mvc.Controller
import upickle.default.Reader
import upickle.default.Writer
import scala.concurrent.ExecutionContext.Implicits.global
import autowire.Core.Request

import scala.concurrent.Future

object RPC extends autowire.Server[String, Reader, Writer] {
  def write[AutowireResult: Writer](r: AutowireResult) = upickle.default.write(r)
  def read[AutowireResult: Reader](p: String) = upickle.default.read[AutowireResult](p)

  val basicApi = route[BasicApi](BasicApiImpl)
  val routes = basicApi
}

class Application @Inject()() extends Controller {

  def index = Action { implicit request =>
    Ok(is.launaskil.views.html.main())
  }

  def api(path: String) = Action.async { implicit request =>
    try {
      val req = upickle.default.read[Request[String]](request.body.asText.get)
      val result = RPC.routes(req)
      result.map { txt =>
        Ok(txt)
      }
    } catch {
      case HttpError(s) => Future.successful(new Status(s))
    }
  }
}
