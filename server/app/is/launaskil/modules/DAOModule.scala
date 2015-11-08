package is.launaskil.modules

import com.google.inject.AbstractModule
import is.launaskil.api.BasicApi
import is.launaskil.api.BasicApiImpl

class DAOModule extends AbstractModule {
  def configure() = {
    bind(classOf[BasicApi])
      .to(classOf[BasicApiImpl])
  }

}
