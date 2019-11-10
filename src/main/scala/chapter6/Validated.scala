package chapter6

import cats.data.Validated
import cats.instances.list._
import cats.syntax.apply._
import cats.implicits._

case class User(name: String, age: Int)

object Validated {

  // exercise 6.4.4. implement a parser using Validated. If problem, return List of errors. If ok return User
  // Rules:
  // name must be specified
  // name must be not blank
  // age must be non negative

  type FormData = Map[String, String]
  type FailFast[A] = Either[List[String], A]
  type FailSlow[A] = Validated[List[String], A]

  def readName(map: FormData): FailFast[String] =
    for {
      value <- getValue("name", map)
      name <- nonBlank(value)
    } yield name

  def readAge(map: FormData): FailFast[Int] =
    for {
      value <- getValue("age", map)
      nonBlankAge <- nonBlank(value)
      age <- parseInt(nonBlankAge)
    } yield age


  def getValue(name: String, map: FormData): FailFast[String] =
    map.get(name).toRight(List(s"$name field does not exist"))

  def parseInt(string: String): FailFast[Int] =
    Either.catchOnly[NumberFormatException](string.toInt).leftMap(
      _ => List(s"$string is not a valid integer")
    )

  def nonBlank(name: String): FailFast[String] =
    Right(name).ensure(List(s"$name cannot be blank"))(_.nonEmpty)



}
