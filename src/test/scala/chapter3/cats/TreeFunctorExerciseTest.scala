package chapter3.cats

import cats.Functor
import tests.UnitTest

class TreeFunctorExerciseTest extends UnitTest {

  "TreeFunctor" should "map over a Tree with leaves only" in {
    import TreeFunctorExercise._
    val tree = Tree.branch(Tree.leaf(1), Tree.leaf(3))
    val expectedTree = Tree.branch(Tree.leaf(5), Tree.leaf(7))
    val mapOperation = Functor[Tree].map(tree)(_ + 4)
    mapOperation shouldBe expectedTree
  }

  it should "map over Tree with branches and leaves" in {
    import TreeFunctorExercise._
    val tree = Tree.branch(Tree.branch(Tree.leaf(1), Tree.leaf(2)), Tree.leaf(3))
    val expectedTree = Tree.branch(Tree.branch(Tree.leaf(5), Tree.leaf(6)), Tree.leaf(7))
    val mapOperation = Functor[Tree].map(tree)(_ + 4)
    mapOperation shouldBe expectedTree
  }

}
