package is.launaskil.api

import is.launaskil.models.Tables

object BasicApiImpl extends BasicApi with Tables {

  def helloLaunaskil(hello: String) = hello + " Launaskil!"
}
