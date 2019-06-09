package chapter2

class Monoid {

  // 1. associativity: 1 + 3 == 3 + 1 is true
  // 2. combination: 1 + 3 = 4, Int + Int = Int
  // 3. empty element: 1 + 0 = 1, 5 + 0 = 5
  //.. above translates into Scala code as:

  trait Monoid[A]{
    def combine(one: A, two: A): A // must be associative
    def empty: A // must be an identity element
  }

  def associativeLaw[A](a: A, b: A, c: A)(implicit m: Monoid[A]): Boolean = {
    m.combine(a, m.combine(b, c)) == m.combine(b, m.combine(a, c))
  }

  def identityLaw[A](a: A)(implicit m: Monoid[A]): Boolean = {
    m.combine(a, m.empty) == a && m.combine(m.empty, a) == a
  }

}
