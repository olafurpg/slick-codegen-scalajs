package is.launaskil.api

import utest.framework.TestSuite
import utest._

object BasicApiImplTest extends TestSuite {

  val tests = TestSuite{
    'basic{
      val msg = "hello"
      val result = BasicApiImpl.helloLaunaskil(msg)
      assert(result.startsWith(msg))
    }
  }
}
