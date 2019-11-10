package chapter6


object SemigroupAndApplicative {

  import cats.Monad
  import cats.syntax.flatMap._
  import cats.syntax.functor._

  def product[M[_]: Monad, A, B](x: M[A], y: M[B]): M[(A, B)] = {
    for {
      xs <- x
      ys <- y
    } yield (xs, ys)
  }

}