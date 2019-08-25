package chapter4

import chapter4.cats.ReaderMonad.Db
import chapter4.cats.ReaderMonad
import tests.UnitTest

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class ReaderMonadTest extends UnitTest{

  val users = Map(
    1 -> "abc",
    2 -> "def"
  )

  val passwords = Map(
    "abc" -> "myPass",
    "def" -> "myPass2"
  )

  val db = Db(users, passwords)


  "findUsername" should "get correct username" in {
    ReaderMonad.findUserName(2).run(db) shouldBe Some("def")
  }

  it should "handle non existing users" in {
    ReaderMonad.findUserName(4).run(db) shouldBe None
  }

  "checkPassword" should "return true for correct password" in {
    ReaderMonad.checkPassword("abc", "myPass").run(db) shouldBe true
  }

  it should "return false for incorrect password" in {
    ReaderMonad.checkPassword("abc", "sadsda").run(db) shouldBe false
  }

  it should "handle non existing user" in {
    ReaderMonad.checkPassword("asdasds", "ad").run(db) shouldBe false
  }

  "checkLogin" should "return true for correct userId and password" in {
    ReaderMonad.checkLogin(2, "myPass2").run(db) shouldBe true
  }

  it should "return false for non existing user" in {
    ReaderMonad.checkLogin(5, "ad").run(db) shouldBe false
  }

  it should "return false for incorrect password" in {
    ReaderMonad.checkLogin(1, "sada").run(db) shouldBe false
  }

}
