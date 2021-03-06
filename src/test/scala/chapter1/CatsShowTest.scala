package chapter1

import tests.UnitTest

class CatsShowTest extends UnitTest {

  val cat = Cat("cat", 10, "grey")

  "PrintableCats" should "print a cat" in {
    import CatsShow.showCat
    import cats.implicits._
    cat.show shouldBe s"Cat is ${cat.age} years old"
  }

  it should "interpolate with show instead of toString" in {
    import CatsShow.showCat
    import cats.implicits._
    show"$cat" shouldBe s"Cat is ${cat.age} years old"
  }

}
