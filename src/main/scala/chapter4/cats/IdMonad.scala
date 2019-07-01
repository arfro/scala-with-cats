package chapter4.cats

import cats.Id

object IdMonad {

  def pure[A](value: A): Id[A] = value
  def map[A, B](initial: Id[A])(fun: A => B): Id[B] = fun(initial)
  def flatMap[A, B](initial: Id[A])(fun: A => Id[B]): Id[B] = fun(initial)
}
