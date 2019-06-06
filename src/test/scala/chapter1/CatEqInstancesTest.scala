package chapter1

import tests.UnitTest
import cats.syntax.eq._

class CatEqInstancesTest extends UnitTest {

  "EqCats" should "allow to compare cat instances with =!=" in {
    import CatEqInstances._
    Cat("cat", 2, "") =!= Cat("ada", 3, "") shouldBe true
  }

}
