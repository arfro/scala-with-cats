package chapter4

import chapter4.cats.IdMonad
import tests.UnitTest

class IdMonadTest extends UnitTest{

  "IdMonad" should "work for pure" in {
    IdMonad.pure(3) shouldBe 3
  }

  it should "work for map" in {
    IdMonad.map(4)(_ + 3) shouldBe 7
  }

  it should "work for flatMap" in {
    IdMonad.flatMap(4)(_ + 4) shouldBe 8
  }
}
