package chapter1

import tests.UnitTest

class PrintableCatsTest extends UnitTest {

  val cat = Cat("cat", 10, "grey")

  "PrintableCats" should "print a cat" in {
    import PrintableCats.showCat
    import cats.implicits._
    cat.show shouldBe s"Cat is ${cat.age} years old"
  }

}
