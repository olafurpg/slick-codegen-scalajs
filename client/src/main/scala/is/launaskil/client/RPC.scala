package is.launaskil.client
import scala.concurrent.ExecutionContext.Implicits.global
import upickle.default._
import org.scalajs.dom

object RPC extends autowire.Client[String, Reader, Writer] {
  def write[Result: Writer](r: Result): String = upickle.default.write(r)
  def read[Result: Reader](p: String): Result = upickle.default.read[Result](p)

  override def doCall(req: Request) = {
    dom.ext.Ajax.post(
      url = "/v1/api/" + req.path.mkString("."),
      data = upickle.default.write(req)
    ).map(_.responseText)
  }
}


