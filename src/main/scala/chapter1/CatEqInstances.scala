package chapter1

import cats.Eq
import cats.syntax.eq._
import cats.instances.string._
import cats.instances.int._

object CatEqInstances {

  // Cats Eq is used to ensure type-safe equality between instances of types and not comparing apples to oranges
  // this is not type safe: 1 == "1". With Cats Eq this wouldn't compile.
  // it's a type safe alternative to 'equals'.

  implicit def eqCat: Eq[Cat] = Eq.instance[Cat] {
    (c1: Cat, c2: Cat) => c1.name === c2.name && c1.age === c2.age
  }

}
