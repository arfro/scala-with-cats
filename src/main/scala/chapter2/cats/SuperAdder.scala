package chapter2.cats

object SuperAdder {

  // add for ints only
  def add(items: List[Int]) = items.reduce(_ + _)

  // add for Option[Int]
  import cats.Monoid // for Monoid
  import cats.syntax.semigroup._ // for |+|

  def addMonoid[A](items: List[A])(implicit monoid: Monoid[A]): A = items.fold(monoid.empty)(_ |+| _)

  // add for Orders!
  case class Order(totalCost: Double, quantity: Double)

  // combining two Orders can be done using a Monoid. There is a monoid per operation, keep in mind subtraction could not be a monoid, because it's not associative!
  implicit val ordersAddingMonoid: Monoid[Order] = new Monoid[Order] {
    override def combine(x: Order, y: Order): Order = Order(x.totalCost + y.totalCost, x.quantity + y.quantity)
    override def empty: Order = Order(0.0, 0.0)
  }


}
