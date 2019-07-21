package chapter4

import chapter4.cats.WriterMonad
import tests.UnitTest
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.{Await, Future}

class WriterMonadTest extends UnitTest{

  "WriterMonad factorial" should "have all messages for factorial of 4" in {
    val result = WriterMonad.factorial(4)
    result.value shouldBe 24
    result.written.size shouldBe 4
  }

  it should "pass test on threads" in {
    val one = Await.result(Future(WriterMonad.factorial(4)), 5.seconds)
    val two = Await.result(Future(WriterMonad.factorial(8)), 5.seconds)
    one.value shouldBe 24
    two.value shouldBe 40320
    one.written.size shouldBe 4
    two.written.size shouldBe 8
  }

}
