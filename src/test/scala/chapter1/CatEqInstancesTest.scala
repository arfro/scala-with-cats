package chapter1

import tests.UnitTest
import cats.syntax.eq._

class CatEqInstancesTest extends UnitTest {


  val cat1 = Cat("cat", 2, "")
  val cat2 = Cat("cat", 22, "")
  
  // I'm not sure how to test syntax of triple equals from cats library, '===' by default refers to triple equals from scalaTest library

  "EqCats" should "allow to compare cat instances" in {
    import CatEqInstances._
    eqCat.eqv(cat1, cat2) shouldBe false
    cat1 =!= cat2 shouldBe true
  }
}
