package chapter4

import chapter4.cats.StateMonad
import tests.UnitTest

class StateMonadTest extends UnitTest{

  "StateMonad - running" should "return a tuple for run" in {
    StateMonad.firstMonad.run(10).value shouldBe (10, "The state is: 10")
  }

  it should "return Int state for runS" in {
    StateMonad.firstMonad.runS(10).value shouldBe 10
  }

  it should "return String value for runA" in {
    StateMonad.firstMonad.runA(10).value shouldBe "The state is: 10"
  }

  "StateMonad - combining" should "return a tuple when two monads combined on run" in {
    StateMonad.monadsCombined.run(10).value shouldBe (40, ("Result in step 1: 20", "Result in step 2: 40"))
  }

  it should "return String value for runA" in {
    StateMonad.monadsCombined.runA(10).value shouldBe ("Result in step 1: 20", "Result in step 2: 40")
  }
}
