package chapter1

import tests.UnitTest

class PrintableTest extends UnitTest {

  val cat = Cat("cat", 10, "grey")

  "Printable" should "format Cat without cats lib" in {
    import PrintableInstances.printableCat
    Printable.format(cat) shouldBe s"Cat's name is ${cat.name}, cat's age is ${cat.age}, cat's colour is ${cat.colour}"
  }

  "PrintableSyntax" should "format Cat without cats lib" in {
    import PrintableSyntax.PrintableOps
    import PrintableInstances.printableCat
    cat.format shouldBe s"Cat's name is ${cat.name}, cat's age is ${cat.age}, cat's colour is ${cat.colour}"
  }

}

