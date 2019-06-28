// 1. Left identity law
// pure(a).flatMap(f) == f(a)

val value = 1
val monad = List(value)

def function(int: Int) = List(int, int)

val leftIdentityLaw =
  monad.flatMap(function) == function(value)

// 2. Right identity law
// passing pure to flatMap does nothing
// m.flatMap(pure) == m

val value2 = 2
val monad2 = List(value2)

val rightIdentityLaw =
  monad2.flatMap(List(_)) == monad2

// 3. Associativity law
// flatMapping over f and then g is the same as flatmapping over g and then f

val value3 = 3
val monad3 = List(value3)

def f(int: Int) = List(int + 3)
def g(int: Int) = List(int + 4)

val associativityLaw =
  monad3.flatMap(g).flatMap(f) == monad3.flatMap(f).flatMap(g)