package chapter2

class Semigroup {

  // it's just the "combine" part of a monoid - because for some data types we can't define "empty" element.
  // For example a type NonEmptyList cannot have an "empty" element - it can therefore implement a Semigroup but not a Monoid
  // in simplification:

  trait Semigroup[A] {
    def combine(a: A, b: A): A
  }

  trait Monoid[A] extends Semigroup[A] {
    def empty: A
  }

  // given the above we can see that whenever we need a Semigroup[A] we can also pass a Monoid[A]

}
