package chapter1

import org.scalatest.{FlatSpec, Matchers}

class PrintableTest extends FlatSpec with Matchers {

  val cat = Cat("cat", 10, "grey")

  "Printable" should "format Cat" in {
    import PrintableInstances.printableCat
    Printable.format(cat) shouldBe s"Cat's name is ${cat.name}, cat's age is ${cat.age}, cat's colour is ${cat.colour}"
  }

  "PrintableSyntax" should "format Cat" in {
    import PrintableSyntax.PrintableOps
    import PrintableInstances.printableCat
    cat.format shouldBe s"Cat's name is ${cat.name}, cat's age is ${cat.age}, cat's colour is ${cat.colour}"
  }

}

