package chapter1

trait Printable[A] { // this is the type class itself, in a form of a trait.
  // i want for certain classes to do a certain thing even though they are not aware of it.
  // for instance i want to be able to print all new case classes without having to modify them.
  // This trait (this type class) is for a given type A and doesn't actually do much apart from setting a "blueprint"
  // for instances that will actually implement this behaviour. It's very behaviour focused.
  def format(value: A): String
}

object PrintableInstances{ // default instances for String and Int, implicits packaged in one object
  implicit val printableInt = new Printable[Int] {
    override def format(value: Int): String = value.toString
  }

  implicit val printableString = new Printable[String] {
    override def format(value: String): String = value
  }

  // instead of having an ugly "printCat" method in companion object for Cat I create a type class instance for case class Cat.
  // this way I have all format functions in one place and its easy to add new ones
  implicit val printableCat = new Printable[Cat] { // this could also go in a companion object of Cat
    override def format(cat: Cat): String = s"Cat's name is ${cat.name}, cat's age is ${cat.age}, cat's colour is ${cat.colour}"
  }
}

object Printable { // interface object - this is what we want to expose
  // We don't want to expose Printable because why would we? it's just a trait, useless as API
  // We don't want to expose PrintableInstance because it would be awkward for users some random implicits
  // .. better to expose it simple - like Printable.format(value) <-
  def format[A](value: A)(implicit implicitFormatter: Printable[A]): String = implicitFormatter.format(value)
  def print[A](value: A)(implicit  implicitFormatter: Printable[A]): Unit = println(value)
}


final case class Cat(name: String, age: Int, colour: String)


