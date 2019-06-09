package chapter2

class BooleanMonoids {

  trait Monoid[A]{
    def combine(one: A, two: A): A // must be associative
    def empty: A // must be an identity element
  }

  implicit val booleanAndMonoid: Monoid[Boolean] = new Monoid[Boolean] {
    override def combine(a: Boolean, b: Boolean): Boolean = a && b
    override def empty: Boolean = true
  }

  implicit val booleanOrMonoid: Monoid[Boolean] = new Monoid[Boolean] {
    override def combine(a: Boolean, b: Boolean): Boolean = a || b
    override def empty: Boolean = false
  }

}
