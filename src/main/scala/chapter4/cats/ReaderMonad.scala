package chapter4.cats

import cats.data.Reader

object ReaderMonad {

  // Writer monad is used to wrap a function A => B using Reader.apply function

  case class Person(name: String, eyeColour: String)

  // this reader monad is constructed by passing a function Person => String as per signature of the monad
  val personName: Reader[Person, String] = Reader(person => person.name)

  // run is used to extract the function outcome
  personName.run(Person("annette", "brown")) // annette


  // the true power of monad lies in its map and flatMap functions though.
  // 'map' extends the computation by passing the result through a function.
  // here I map over an existing personName monad:
  val greetPerson: Reader[Person, String] = personName.map(name => s"Hi $name")
  greetPerson.run(Person("annette", "brown")) // Hi annette


  // 'flatMap' allows to combine readers that depend on the same input type
  val eyeColour: Reader[Person, String] = Reader(person => s"Your eyes are ${person.eyeColour}")
  eyeColour.run(Person("annette", "brown")) // Your eyes are brown

  // here is how I combine them:
  val greetAndEyeCol = for {
    greet <- greetPerson
    eyeCol <- eyeColour
  } yield s"$greet. $eyeCol." // this is still just a "blueprint". We can now pass a Person to 'greetAndEyeCol':

  greetAndEyeCol(Person("annette", "brown")) // Hi annette. Your eye colour is brown



  // exercise 4.8.3.
  case class Db(usernames: Map[Int, String], passwords: Map[String, String])

  type DBReader[T] = Reader[Db, T]

  def findUserName(userId: Int): DBReader[Option[String]] = Reader(db => db.usernames.get(userId))

  def checkPassword(username: String, password: String): DBReader[Boolean] =
    Reader(db => db.passwords.get(username).contains(password))

  def checkLogin(userId: Int, password: String): DBReader[Boolean] =
    for {
      user <- findUserName(userId)
      res <- user.map(name => checkPassword(name, password)).getOrElse{
        Reader[Db, Boolean](_ => false)
      }
    } yield res


}
