package chapter4.cats

import cats.syntax.either._

import scala.util.Try

object EitherMonad {

  // cool ways to work with Either monad:

// 1. Creating instances with smart constructors that will return type of Either instead Right or Left

  // Either[String, Int]
  val smart = 4.asRight[String]
  // Right(3)
  val notSmart = Right(3)

  // Either[Int, Int]
  val smart2 = 3.asLeft[Int]
  // Left(3)
  val notSmart2 = Left(3)


// 2. catchOnly and catchOnlyFatal- capturing exceptions as instances of Either. Could be good for working with Java libs

  // Either[NumberFormatException, Int]
  // Left(java.lang.NumberFormatException: ...)
  val catchOnlyEither = Either.catchOnly[NumberFormatException]("lala".toInt)

  // Either[Throwable, Nothing]
  // Left(java.lang.RuntimeException: yikes)
  val catchNonFatal = Either.catchNonFatal(sys.error("yikes"))



// 3. fromTry - create either from Try, again, good for Java code

  // Either[Throwable, Int]
  // Left(java.lang.NumberFormatException: ...)
  val fromTry = Either.fromTry(Try("lala".toInt))



// 4. fromOption - create either from Option

  // Either[String, Int]
  // Left(ah jayzas)
  val fromOption = Either.fromOption[String, Int](None, "ah jayzas")



// 5. orElse and getOrElse

  // 0
  val getOrElse = "lala".asLeft[Int].getOrElse(0)

  // Right(2)
  val orElse = "lala".asLeft[Int].orElse(2.asRight[Int])


// 6. ensure - allows to check whether right hand value meets predicate

  // Either[String, Int]
  // Left("must be greater than 30")
  val ensure = 33.asRight[Int].ensure("must be greater than 30")(_ > 30)



// 7. recover and recoverWith (similar to Future's recover and recoverWith)

  // Either[String, Int]
  // Right(-1)
  val recover = "problem".asLeft[Int].recover{
    case string: String => -1
  }

  // Either[String, Int]
  // Right(-1)
  val recoverWith = "problem".asLeft[Int].recoverWith{
    case string: String => Right(-1)
  }


// 8. swap - swap Left for Right!

  // Either[String, Int]
  // Right(123)
  val noSwap = 3.asRight[String]

  // Either[Int, String]
  // Left(123)
  val swap2 = 3.asRight[String].swap

// 9. toOption, toList, toTry etc...
  
  // Some(12)
  val toOption = 12.asRight[String].toOption
  // None
  val toOption2 = 12.asLeft[String].toOption

}
