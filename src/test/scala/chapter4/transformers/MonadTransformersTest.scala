package chapter4.transformers

import cats.data.EitherT

import scala.concurrent.Future
import chapter4.cats.transformers.MonadTransformers
import cats.instances.future._

import scala.concurrent.ExecutionContext.Implicits.global
import tests.UnitTest

import scala.util.Success

class MonadTransformersTest extends UnitTest{

  private val powerLevelsSource = Map(
    "Jazz" -> 6,
    "Bumblebee" -> 8,
    "Hot Rod" -> 10
  )


  // TODO: find out why those tests fail even though the output is correct. They fail with 'EitherT(Future(Success(Right(6)))) was not equal to EitherT(Future(Success(Right(6))))

  "MonadTransformers - power levels get" should "work for an existing record" in {
    MonadTransformers.getPowerLevel("Jazz", powerLevelsSource) shouldBe EitherT.right(Future(6))
  }

  it should "work for a non-existing record" in {
    MonadTransformers.getPowerLevel("hello", powerLevelsSource) shouldBe EitherT.left(Future.successful("hello does not exist"))
  }

}
