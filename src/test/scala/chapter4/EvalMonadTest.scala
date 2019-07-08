package chapter4

import chapter4.cats.EvalMonad
import tests.UnitTest

class EvalMonadTest extends UnitTest{

  "EvalMonad eval factorial" should "work for a small integer" in {
    EvalMonad.evalFactorial(12).value shouldBe 479001600
  }

  it should "work for 0" in {
    EvalMonad.evalFactorial(0).value shouldBe 0
  }

  it should "work for large integer" in {
    EvalMonad.evalFactorial(5000).value > 1000000000 shouldBe true
  }

  "EvalMonad stackSafeFoldRight" should "work for a non empty list" in {
    EvalMonad.stackSafeFoldRight(List(1,2,3), "")(_ + _) shouldBe "123"
  }

}
