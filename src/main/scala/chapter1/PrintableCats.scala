package chapter1

import cats.Show
// Show is cats equivalent of Printable type class I worked on. It makes it more dev friendly to print classes and so that we can avoid using .toString

object PrintableCats {

  implicit val showCat: Show[Cat] = Show.show(cat => s"Cat is ${cat.age} years old")

}
