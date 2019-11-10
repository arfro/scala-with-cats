package chapter6

import tests.UnitTest

class ValidatedTest extends UnitTest {

  "Validated - readName" should "fail fast if name empty" in {
    Validated.readName(Map("name" -> "")) shouldBe Left(List(" cannot be blank"))
  }

  it should "fail fast if no fields are specified" in {
    Validated.readName(Map("abc" -> "")) shouldBe Left(List("name field does not exist"))
  }

  it should "go ok if name correct" in {
    Validated.readName(Map("name" -> "Patricia")) shouldBe Right("Patricia")
  }

  "Validated - readAge" should "fail fast if age is not a number" in {
    Validated.readAge(Map("age" -> "abc")) shouldBe Left(List("abc is not a valid integer"))
  }

  it should "go ok if age is a valid number" in {
    Validated.readAge(Map("age" -> "123")) shouldBe Right(123)
  }

}
