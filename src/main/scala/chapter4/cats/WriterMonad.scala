package chapter4.cats

import cats.data.Writer
import cats.syntax.applicative._
import cats.syntax.writer._
import cats.instances.vector._


object WriterMonad {

  // Writer monad is used to carry a message along with a computation.
  val writerMonad = Writer(
      Vector(
        "message 1",
        "message 2"
      ), 1234)

  // extract messages
  writerMonad.written

  // extract values
  writerMonad.value


  def slowly[A](body: => A) = try body finally Thread.sleep(100)

  type Logged[A] = Writer[Vector[String], A]

  def factorial(n: Int): Logged[Int] = {

    for {
        ans <- {
          if (n == 1) 1.pure[Logged]
          else {
            slowly (
              factorial (n - 1).map (_ * n)
            )
          }
        }
      _ <- Vector(s"fac $n $ans").tell
    } yield ans
  }


}
