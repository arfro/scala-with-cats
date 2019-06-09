package chapter2

import tests.UnitTest

class SetMonoidsTest extends UnitTest {

  "SetMonoidsAndSemigroups - union monoid" should "combine two sets of ints correctly" in {
    import chapter2.SetMonoidsAndSemigroups.setUnionMonoid
    setUnionMonoid.combine(Set(1, 2), Set(2, 4)) shouldBe Set(1, 2, 4)
  }

  it should "combine correctly with empty" in {
    import chapter2.SetMonoidsAndSemigroups.setUnionMonoid
    setUnionMonoid.combine(Set(1, 2), setUnionMonoid.empty) shouldBe Set(1, 2)
  }

  "SetMonoidsAndSemigroups - intersection semigroup" should "combine two sets of ints correctly" in {
    import chapter2.SetMonoidsAndSemigroups.setIntersectionSemigroup
    setIntersectionSemigroup.combine(Set(1, 2), Set(2, 4)) shouldBe Set(2)
  }

}
