package chapter1

import tests.UnitTest
import cats.syntax.eq._

class CatEqInstancesTest extends UnitTest {


  val cat1 = Cat("cat", 2, "")
  val cat2 = Cat("cat", 22, "")

  "EqCats" should "allow to compare Cat instances" in {
    import CatEqInstances._
    eqCat.eqv(cat1, cat2) shouldBe false
    cat1 =!= cat2 shouldBe true
  }

  it should "allow to compare Option of Cat instances" in {
    import CatEqInstances._
    import cats.instances.option._
    (Some(cat1): Option[Cat]) =!= (None : Option[Cat]) shouldBe true
  }
}
