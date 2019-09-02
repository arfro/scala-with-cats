package chapter4.cats

import cats.data.State

object StateMonad {

  val firstMonad = State[Int, String] {
    state => (state, s"The state is: $state")
  }

  val stepOneMonad = State[Int, String] {
    number => {
      val answer = number + 10
      (answer, s"Result in step 1: $answer")
    }
  }

  val stepTwoMonad = State[Int, String] {
    number => {
      val answer = number * 2
      (answer, s"Result in step 2: $answer")
    }
  }

  val monadsCombined = for {
    first <- stepOneMonad // "first" will come in as argument to flatmap below!
    second <- stepTwoMonad
  } yield (first, second)


}
