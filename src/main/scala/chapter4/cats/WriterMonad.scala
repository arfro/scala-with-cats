package chapter4.cats

import cats.data.Writer
import cats.syntax.applicative._
import cats.syntax.writer._
import cats.instances.vector._


object WriterMonad {

  // good practice to alias the Writer monad:
  type Logged[A] = Writer[Vector[String], A]

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

  // -----------------
  // SYNTAX VERSION 1:
  // When you have the value AND the log
  // -----------------

  import cats.data.Writer
  import cats.instances.vector._

  val x = Writer(Vector("some intermediary computation"), 3)
  val y = Writer(Vector("another intermediary computation"), 4)

  val z = for {
    a <- x
    b <- y
  } yield a + b
  // WriterT(Vector(some intermediary computation, another intermediary computation),7)

  println(z.value)
  // 7

  println(z.written)
  // Vector(some intermediary computation, another intermediary computation)

  // -----------------
  // SYNTAX VERSION 2:
  // When you have the value OR the log
  // -----------------

  import cats.syntax.applicative._
  import cats.syntax.writer._

  val writer1 = for {
    a <- 10.pure[Logged]                  // no log
    _ <- Vector("a", "b", "c").tell       // no value, but log still gets appended
    b <- 32.writer(Vector("x", "y", "z")) // log and value
  } yield a + b                           // map transforms the result

  println(writer1)
  // WriterT((Vector(a, b, c, x, y, z),42))


  // exercise

  def slowly[A](body: => A) = try body finally Thread.sleep(100)


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
