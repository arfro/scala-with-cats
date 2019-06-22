These are my non-code notes from the book. This is all in my words.

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

### Functors in real life

#### Futures 
A functor that allows sequencing asynchronous operations, it works by applying those operations only once predecessors are complete. Once the Future is complete, then only `map` executes. Futures always start immediately, it doesn't let the program dictate when to start.

#### Functions (?!)
Think about it - function composition is sequencing. And functors are all about sequencing!

 
## Higher kinds and Type constructors
Kinds are like types for types. They describe the number of "holes" to fill in a type. E.g. List has one "hole" - it can be `List[String]` or `List[Int]` or anything else. 

<b>`List` is a type constructor</b>
<b>`List[A]` is a type</b>


