package chapter4.cats.transformers

import cats.data.EitherT

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import cats.instances.future._

object MonadTransformers {

  // exercise 5.4
  type Response[A] = EitherT[Future, String, A]

  def getPowerLevel(search: String, source: Map[String, Int]): Response[Int] = {
    source.get(search) match {
      case Some(result) => EitherT.right(Future(result))
      case None => EitherT.left(Future(s"$search does not exist"))
    }
  }



}
