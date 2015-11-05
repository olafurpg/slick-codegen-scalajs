package is.launaskil.client


import autowire._
import is.launaskil.api.BasicApi
import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.ReactDOM
import japgolly.scalajs.react.vdom.prefix_<^._

import org.scalajs.dom.document

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport
import scala.concurrent.ExecutionContext.Implicits.global

object Main extends JSApp {

  val HelloLaunaskil =
    ReactComponentB[String]("Hello <Launaskil>")
      .render(msg => <.div("", msg.props))
      .build

  @JSExport
  override def main(): Unit = {
    RPC[BasicApi].helloLaunaskil("Hello").call().map { msg =>
      ReactDOM.render(HelloLaunaskil(msg), document.getElementById("app"))
    }
  }

}
