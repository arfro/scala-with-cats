These are my non-code notes from _Scala with Cats_ by Noel Welsh and Dave Gurnell. A mix of my own words and cool quotes. Get the book, it's awesome!

## Semigroup
It has to do with an <b>operation</b>, NOT a data type. Semigroup is an operation that:
* is associative (`1 + 3` is the same as `3 + 1`)
* combines two elements (`1 + 3`)
* DOESN'T have an _empty_ element

For example:
* addition on positive numbers (0 is not a positive number, so this operation does not have an _empty_ element)


## Monoid
It's a semigroup too. It has to do with an <b>operation</b>, NOT a data type. A monoid is an operation that:
* is associative (`1 + 3` is the same as `3 + 1`)
* has an _empty_ element (`1 + 0` or `0 + 1` is the same as `1`)
* combines two elements (`1 + 2`)

The main thing that had me confused for a long time was that monoid was a type or a container. <b>It's not</b>. As mentioned it's a set of properties on an operation on a certain type. For example:
* addition for Ints is a monoid
* multiplication for Doubles is a monoid
* concatenation for String is a monoid

Why is it useful? We can combine types that are not intuitively combinable. It's easy to imagine what does it mean to combine (add) two Integers. But what does it mean to combine five Orders? This can be represented as e.g `addingOrdersMonoid` where our _empty_ element is an order with all 0's and combine combines each respective field.

### Monoid in real life
#### Big data (system architecture)
We distribute data analysis to a few machines, once we get the results returned we need to combine them into a final result. Vast majority of use cases here would be monoids and we can build a very expressive and powerful analytics like this! 
* find out how many unique users logged in - build `Set[User]` for each data portion and then combine them. 


#### Distributed systems (system architecture)
* find out a united state of the system - if machine A got an update but machine B didn't we could combine the updates and this way form a consistent state for each machine.


## Functor

In simple terms: anything that has a `map` method. From _Scala with cats_:
>An abstraction that allows us to represent sequences of operations within a context such as List 

e.g. `List(1, 2, 3).map(function1).map(function2).map(function3)`

`map` should not be thought of as in "iterate through this sequence" but "apply this function to each element". `map` never changes the context of type it's called on so it can be chained as shown above. 

Functor in cats also provides `lift` method which converts a function `A => B` to work in on a functor so to `F[A] => F[B]`. 

Functor wraps a type (let's say like from the code examples - `Tree[A]`). We will need an implicit Functor instance for `Tree[A]` that implements map for `Tree` - this will need to be in a scope so we can call `Functor[Tree].map(tree)(function)` - otherwise compiler doesn't know how to `map` over `Tree`.


## Monad
Informally, anything with a constructor and a `flatMap` method. e.g. `Future[Int]` - `Future` is a type constructor and `Future[Int]` is a type. For comprehension is a special syntax that supports composing monads. 

In the simplest, it's a mechanism for _sequencing computations_.

`List` - thinking in a monadic way: `List` is a set of intermediate results! Flatmapping over it gives us combinations and permutations
`Future` - monad that sequences asynchronous computations. Flatmapping it takes care of the underlaying stuff like thread pools or schedulers.

Formally monads have to abide to laws (checkout chapter4/MonadWorksheet.sc for code:
* have `pure` function - create a monadic context from a plain value, e.g. `Future[Int]` from `Int`
* have `flatMap` function - sequence computations: extract from context, compute, generate next context.
* <b>Left identity</b> - calling pure on `a` and flatmapping with `function` is the same as calling `function(a). In other words: pure(a).flatmap(function) == function(a) // here pure is on the left
* <b>Right identity law</b> - passing `pure` to flatMap is the same as doing nothing. In other words: * <b>Right identity law</b> - passing `pure` to flatMap is the same as doing nothing. In other words: 
* <b>Identity law</b> - flatmapping with f and then g is the same as flatmapping with g and then f

### Identity monad
Monad that allows us to call monadic method using plain values, so if `f(a: M[Int])` takes a monad for type Int if we change the type to `Id[Int]` we can just call `f(2)` with plain Int. This monad could be useful when in production we're running code asynchrounously using `Future` but for tests we want to run it synchronously. We then use `Id`. As it's identity calling this monad with pure, map or flatmap with always return the value itself.

### Either monad
`Right` represents success and supports map and flatMap directly. It's much easier with for comprehensions. It's usually used as a "fail-fast" error handling.

Rules/tips: 
* Better to use smart constructors to avoid type problems: `3.asRight[Int]` vs `Right(3)`
* you can transform Eithers a lot. Just to name a few methods: toOption, fromTry, catchOnly.

(All above applies to Scala 2.12, before 2.12 it didn't have map and flatMap, in 2.12 it was redesigned but you could use cats.syntax.either._ to make Either right biased prior to 2.12)

### Try monad
Just like `Either` only that `Left` is `Throwable`

### Eval monad
A monad that allows to abstract over different _models of evaluation_ (lazy vs eager):
* Eval.now() - similar to val, eager and memoized
* Eval.always() - similar to def, lazy computation, not memoized
* Eval.later() - similar to lazy val, lazdy and memoized

Trampolining - a technique where you can nest calls to map and flatMap without consuming stack frames - that means it is "stack safe" and won't blow up like recursion would. There are still limits though, it creates a chain of functions on the heap. Eval.defer protects the recursive call basically.

### Writer monad
Lets you carry the log with computation. Used to record errors or messages about computation and extract the log at the end of computation. It's used in multithreaded environment to have log messages that are not mixed up. `Writer[W, A]` <- log of type `W` and result type `A`. Best choice for a log type is `Vector`, or anything else that's easily concatenated.

`flatMap` over a Writer monad allows to combine them, so usage would be as follows:

```java
import cats.data.Writer 
import cats.instances.vector._

val x = Writer(Vector("some intermediary computation"), 3)
val y = Writer(Vector("another intermediary computation"), 4)

val z = for {
  a <- x
  b <- y
} yield a + b
// WriterT(Vector(some intermediary computation, another intermediary computation),7)

println(z.value)
// 7

println(z.written)
// Vector(some intermediary computation, another intermediary computation)
```

(See code for alternative syntax!)

### Reader monad
Reader monad allows to sequence operations that depend on some input. Reader wraps up functions of one argument and allows for composing them.

One common use is <b>dependency injection</b>. Using a reader monad we can chain a number of computations and produce one large operation that takes one configuration as an argument. To do this we create a set of Reader monads, combine them with map and flatMap and then run them to add a config at the end.

We create a Reader instance `Reader[A, B]` from a function `A => B` using `Reader.apply`.

The true power of Reader monad comes from its `map` and `flatmap` functions though. This is the heart of the above mentioned chaining computations power. Reader monad allows you to e.g. first describe want you want to do with your data and the pass the dependency, so: getUserId, checkPassword and THEN only run passing database to a "run" method.


## Error handling
#### Either
It's good practice to use algebraic data types with Either - if we use a sum ADT for errors we expect we can nicely pattern match. If we also create a type alias it becomes very intuitive: `type LoginResult = Either[LoginError, User]` 


 
 
## Algrebraic data types
Similar to enum. It's all about <b>data</b>. There is three types of algebraic data types:
* sum aka. <b>IS A / OR</b>  (sealed trait and case classes, all case classes are all options for this data type)
* product aka. <b>HAS A / AND</b> (case class with arguments, arguments show there can be infinite options for this data type)
* hybrid 

## Higher kinds and Type constructors
Kinds are like types for types. They describe the number of "holes" to fill in a type. E.g. List has one "hole" - it can be `List[String]` or `List[Int]` or anything else. 

<b>`List` is a type constructor</b><br>
<b>`List[A]` is a type</b>

Think of an analogy to functions and values. Function is like a type contructor and values are like types. Values and types are "concrete", functions and type constructors need _something_ to work (function (usually) needs an argument and type constructor needs a type).

In Scala we <b>declare</b> type constructors using underscore like `def myMethod[F[_]]`. Once they're declared we refer to them as simple identifiers like `val functor = Functor.apply[F]`

When working with higher kinded types we need to import scala.language.higherKinds to avoid warnings from the compiler. 


## Eager, Lazy, Memoized
Three types of models of evaluations. 

#### Eager evaluation
Evaluation happens immediately
#### Lazy evaluation
Evaluation happens on access
#### Memoized evaluation
Evaluation happens on first access and the result is then cached

`val` is eager
`lazy val` is lazy and memoized
`def` is lazy and NOT memoized