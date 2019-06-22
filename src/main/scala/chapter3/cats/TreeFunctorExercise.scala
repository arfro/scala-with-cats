package chapter3.cats

import cats.Functor


object TreeFunctorExercise {

  sealed trait Tree[+A]
  final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
  final case class Leaf[A](value: A) extends Tree[A]

  // without those constructors compiler would have a problem with invariance.
  // There would be an implicit instance of a Functor for Tree but NOT for Branch or Leaf.
  // Doing it like below ensures we always reason about type Tree[A] so no problems with a compiler
  // now we will call like Tree.branch(Leaf(1)).map(function)
  object Tree {
    def branch[A](left: Tree[A], right: Tree[A]): Tree[A] = Branch(left, right)
    def leaf[A](value: A): Tree[A] = Leaf(value)
  }


  // we want to be able to map over Tree so call something like Tree.map(function). That means we need a functor constructed with type Tree
  implicit val functorTreeImplicit: Functor[Tree] = new Functor[Tree] {
    def map[A, B](tree: Tree[A])(function: A => B): Tree[B] =
      tree match {
        // we need the same structure so pattern match on Branch needs to give us Branch
        case Branch(left, right) => Branch(map(left)(function), map(right)(function))
        // and pattern match on Leaf needs to give us Leaf
        case Leaf(value) => Leaf(function(value))
      }
  }

}
