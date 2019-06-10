package chapter2.cats

import chapter2.cats.SuperAdder.Order
import tests.UnitTest

class SuperAdderTest extends UnitTest {

  "SuperAdder - add" should "add integers" in {
    SuperAdder.add(List(1, 2)) shouldBe 3
  }

  "SupperAdder - addOptions" should "add integers" in {
    import cats.instances.int._
    SuperAdder.addMonoid(List(1,2,3)) shouldBe 6
  }

  it should "add options of ints" in {
    import cats.instances.option._
    import cats.instances.int._
    SuperAdder.addMonoid(List(Some(1), None, Some(3))) shouldBe Some(4)
  }

  "SupperAdder - adding orders" should "add two orders" in {
    import SuperAdder.ordersAddingMonoid
    val order1 = Order(2, 4)
    val order2 = Order(1, 3)
    SuperAdder.addMonoid(List(order1, order2)) shouldBe Order(3, 7)
  }
}
