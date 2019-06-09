package chapter2

object SetMonoidsAndSemigroups {

  trait SetSemigroup[A] {
    def combine(one: A, two: A): A // must be associative
  }
  trait SetMonoid[A] extends SetSemigroup[A] {
    def empty: A // must be an identity element
  }

  implicit def setUnionMonoid[A]: SetMonoid[Set[A]] = new SetMonoid[Set[A]] {
    override def combine(a: Set[A], b: Set[A]): Set[A] = a union b
    override def empty: Set[A] = Set.empty[A]
  }

  // intersection has no "empty" so it's a semigroup
  implicit def setIntersectionSemigroup[A]: SetSemigroup[Set[A]] = new SetSemigroup[Set[A]] {
    override def combine(a: Set[A], b: Set[A]): Set[A] = a intersect b
  }

}
