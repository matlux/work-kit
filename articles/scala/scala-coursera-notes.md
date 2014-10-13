

## Week1

### Elements fo Programming

Call-by-value equivalent to Call-by-name: reduced to the same final value (lambda calculus) only if no side effects.

Call-by-value advantage: evaluated only once (Eager evaluation)

Call-by-name advantage: not evaluated at all if not used (lazy evaluation)


### Evaluation Strategies and Termination

if CBV evaluation of expr e terminates
then CBN evaluation of expr e terminates too

other direction is not true

def constOne(x: Int, y: => Int) = 1
CBV:  x: Int
CBN:  y: => Int

### Conditionals and Value Definitions

if-else is an expression

rewrite rules for boolean expr:
* !true -> false
* false -> true
* true && e -> e
* false && e -> false
* true || e -> true
* false || e -> e

(short circuit operations)

CBV:
val x = 42

CBN:
def x = 42


def x = loop
(terminates)


(somelongexpression 
+ somelongexpression)

somelongexpression +
somelongexpression



### Tail Recursion

#### Application Rewriting rule

This is the a substitution:

    def f(x1,x2,..xn) = B; …. f(v1,..,vn)
    ->
    def f(x1,x2,..xn) = B; …. [v1/x1,..,vn/xn]


Donald Knuth: Premature optimisation is the root of all evil

## Week 2

### Higher-Order Functions

Exercise 2

```scala
  def sum(f: Int => Int)(a: Int, b: Int): Int = {
    def loop(a: Int, acc: Int): Int = {
      if (a > b) acc
      else loop(a+1,f(a) + acc)
    }
    loop(a,0)
  
  }                                               //> sum: (f: Int => Int)(a: Int, b: Int)Int
  
  sum((x: Int) => x)( 1, 3)                      //> res1: Int = 6
```

### Currying

#### Expansion of Multiple Parameters Lists

A curried function:
```scala
def f(args1)…(argsn) = E
```

where n > 1 is equivalent to

```scala
def f(args1)…(argsn-1) = {def g(argsn) =E; g}
```

In short:

```scala
def f(args1)…(argsn-1) = argsn => E
```

#### Expansion of Multiple Parameters Lists

By repeating the process n times
```scala
def f(args1)…(argsn-1)(argsn) = E
```

is equivalent to:

```scala
def f = (args1 =>(args2 => …(argsn => E)…))
```

This style of definition and function application is called currying, named after Haskell Brooks Curry. Also goes back to Schoenfinkel and Frege.


#### More Function Types

what is the type of :
```scala
def sum(f: Int => Int)(a: Int, b: Int): Int = …
```

Type is:
```scala
(Int => Int) => (Int, Int) => Int
```

#### Example Finding Fixed Points

```scala
  val tolerance = 0.001                           //> tolerance  : Double = 0.0010
  
  def isCloseEnough(x: Double, y : Double) =
      abs((x - y)/x) /x < tolerance               //> isCloseEnough: (x: Double, y: Double)Boolean
  
  def fixedPoint(f: Double => Double)(firstGuess: Double): Double = {
    def iterate(guess: Double) : Double = {
      val next = f(guess)
      if (isCloseEnough(guess,next)) next
      else iterate(next)
    }
    iterate(firstGuess)
  }                                               //> fixedPoint: (f: Double => Double)(firstGuess: Double)Double
  
  fixedPoint(x => 1 + x/2)(1)                     //> res5: Double = 1.99609375
  
  def averageDamp(f: Double => Double)( x: Double): Double = (x + f(x))/2
  
  def sqrt(x: Double) = fixedPoint(averageDamp(y => x/y))(1.0)
                                                  //> sqrt: (x: Double)Double

  sqrt(2)                                         //> res7: Double = 1.4142135623746899
```

#### Scala Syntax Summary

```
Type		= SimpleType | FunctionType
FunctionType 	= SimpleType ‘=>’ Type
		| ‘(‘ [Types] ‘)’ ‘=>’ Type
SimpleType	= Ident
Types		= Type {‘,’ Type}

```

A type can be:
* numeric type
* Boolean
* String
* function type


#### More Fun With Rational

* `require` is used to enforce a precondition on the caller of the a function
* `assert` is used as to check the code of the function itself

#### Evaluation and Operator

Precedence Rules

(all letters)
|
^
&
< >
= !
:
+ -
* / %
(all other special characters)

## Week3

### Class Hierachies

Abstract classes can contain methods that are not defined.


Mini implementation of a Persistent data structure:
```scala
abstract class IntSet {
  def incl(x: Int): IntSet
  def contains(x: Int): Boolean
}

class Empty extends IntSet {
  def contains(x: Int): Boolean = false
  def incl(x: Int): IntSet = new NonEmpty(x,new Empty, new Empty)
}

class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet{
  def incl(x: Int): IntSet =
    if (x < elem) new NonEmpty(elem, left incl x, right)
    else if (x > elem) new NonEmpty(elem, left, right incl x)
    else this
  def contains(x: Int): Boolean =
    if (x < elem) left contains x
    else if (x > elem) right contains x
    else true
}
```


Classes can override the methods in their base classes (use override modifier) :
```scala
abstract class Base {
  def foo = 1
  def bar: Int
}

class Sub extends Base {
  override def foo = 2
  def bar = 3
}
```

#### Dynamic Binding

Object-oriented languages implement `dynamic method dispatch`

This means that the code invoked by a method call depends on the runtime type of the object that contains the method.

Example:

```scala
Empty contains 1
```


#### Something to Ponder

Dynamic dispatch of methods is analogous to calls to higher-order functions.

Question: Can we implement one concept in terms of the other?

* Objects in terms of higher-order functions?
* Higher-order functions in terms of Objects?


### How Classes are Organized

#### Forms of Imports

```scala
import week3.Rational  		// named import
import week3.{Rational, Hello}  // named import
import week3._			// Wildcard import
```

#### Automatic Import

* all members of package scala
* all members of package java.lang
* all members of the singleton object scala.Predef

FQN of some types

Int 


#### Traits

a trait is declared like an abstract class, just with `trait` instead of `abstract class`


Example:
```scala
class Square extends Shape with Planar with Movable
```

Traits resemble interfaces in java, but are more powerful because they can contain fields and contrite methods

Notes: Difference between class and traits
`traits` cannot have (value) parameters, only classes can.


#### Scala’s class Hierarchy

![class hierarchy](http://www.scala-lang.org/old/node/71%3Fsize=_original.html#)

Top types

Any	== !=	equals hashCode toString

AnyRef	java.lang.Object
	
AnyVal	primitives



Nothing is subtype of any other type.
* abnormal termination
* element type of an empty collection

#### the Null type

Every reference class type also has null a a value

The type of null is Null

Null is a subtype of every class that inherits from Objects; it is incompatible with subtypes of AnyVal.


```scala
val x = null		// x : Null
val y: Sting = null	// y: String
val z: Int = null	// erro: type mismatch
```

### Polymorphism

#### Cons-Lists

Nil	the empty list
Cons	

#### Type parameters


val is initial when first initialised
def is executed only when its called

#### Type erasure

Type parameters do not affect evaluation in Scala.

All type parameters and type arguments are removed before evaluating the program. This is called `type erasure`.

Languages that use type erasure Java, Scala oCaml, Haskell

two types of polymorphism:
* subtyping
* generics


## Week4

### Functions as Objects

The function type A => B is just an abbreviation for the class scala.Function1[A,B]

```scala
trait Function1[A,B] {
  def apply(x: A): B
}
```

#### Expansion of Function Value

An anonymous function such as
```scala
(x: Int) =>  x * x
```

is expanded to:
```scala
{ class AnonFn extends Function1[Int,Int] {
    def apply(x: Int) = x * x
  }
  new AnonFn
}
```

or shorter:
is expanded to:
```scala
new Function1[Int,Int] {
  def apply(x: Int) = x * x
}
```

#### Expansion of Function Calls

A function call `f(a,b)` where f is a value of some class type. is expanded to:

```scala
f.apply(a,b)
```

So the OO-translation of 

```scala
val f = (x: Int) => x * x
f(7)
```

would be

```scala
val f = new Function1[Int,Int]{
    def apply(x: Int) => x * x
  }
f(7)
```

Note that a method such as 
  def f(x: Int): Boolean = …

is not itself a function value.

But if f is used in a place where a Function type is expected, it is converted automatically to the function value

```scala
(x: Int) => f(x)	// eta-expansion (lambda calculus)
```

or
```scala
new Function1[Int,Int] {
  def apply(x: Int) = f(x)
}
```

### Subtyping and Generics

Two forms:
* sub typing   (traditionally from OOP)
* generics     (traditionally from FP)

Two main areas of interations:
* bounds
* variance


#### Type Bounds


##### Upper Bound
```scala
def assertAllPos[S <: IntSet](r: S): S = …
```

Here `<: IntSet` is the upper bound of the type parameter S.

* `S <: T` means S is a subtype of T, and
* `S >: T` means S is a supertype of T, of T is a subtype of S.

##### Lower Bound

[S >: NonEmpty]

So `S` could be one of NonEmpty, InSet,AnyRef, or Any.

##### Mixed Bounds


[S >: NonEmpty <: IntSet]



#### Covariance

Given 

    NonEmpty <: IntSet

is

    List[NonEmpty] <: List[IntSet]

#### the Liskov Substitution Principle

If `A <: B`, then everything one can do with a value of type B one  should also be able to do with a value of type A.

Or more formally:

Let `q(x)` be a property provable about objects `x` of type `B`.
Then `q(y)` should be provable for objects `y` of type `A` where `A <: B`

### Objects Everywhere


### Variance

Definition:

Let `C[T]` be a parametrised type and `A`, `B` types such that `A <: B`.
Three possible relationships:

* `C[A] <: C[B]`                                      C is Covariant
* `C[A] >: C[B]`                                      C is Contravariant
* neither `C[A]` nor `C[B]` is a subtype of the other C is nonvariant or invariant 


* `class C[+A] {…}`    C is covariant
* `class C[-A] {…}`    C is contra variant
* `class C[A] {…}`     C is non variant


For example:

```scala 
type A = IntSet => NonEmpty
type B = NonEmpty => IntSet
```

According to the Liskov Substitution Principle:

`A <: B`

#### Typing Rules for Functions

if A2 <: A1 and B1 <: B2

```scala
A1 => B1 <: A2 => B2
```

#### Function Trait Declaration

```scala
trait Function1[-T,+U] {
  def apply(x: T): U
}
```

#### Variance Checks

```scala
class Array[+T] {
  def update(x: T) …
}
```

List 
```scala
def prepend[U  >: T](elem : U): List[U] = new Cons(elem,this)
```

```scala
  def f(xs: List[NonEmpty], x: Empty) = xs prepend x
```

returns:
`List[IntSet]`



List example
```scala
trait List[+T] {
  def isEmtpy: Boolean
  def head: T
  def tail: List[T]
  def nth(n: Int): T
  def prepend[U  >: T](elem : U): List[U] = new Cons(elem,this)
}


class Cons[T](val head: T, val tail: List[T]) extends List[T] {
  def isEmtpy = false
  def nth(n: Int): T = {
    if (n==0) head else tail.nth(n-1)
  }
}
object Nil extends List[Nothing] {
  def isEmtpy = true
  def head: Nothing = throw new NoSuchElementException("Nil.head")
  def tail: Nothing = throw new NoSuchElementException("Nil.tail")
  def nth(n: Int): Nothing = throw new IndexOutOfBoundsException();

}

object List {
  def apply[T](a: T) = new Cons(a, Nil)
  def apply[T](a: T, b: T) = new Cons(a,new Cons(b, Nil))
  def apply[T](a: T, b: T, c: T) = new Cons(a,new Cons(b,new Cons(c, Nil)))
}
```

### Decomposition

### Pattern Matching

#### Case Classes

```scala
object Number {
  def apply(n: Int) = new Number(n)
}
```

#### What do Patterns Match?

* A constructor pattern c(p1,…,pn) matches all the values of type C (or subtype) which have been constructed with p1,…,pn
* a variable pattern x matches any value and binds the name of the variable to this value
* A constant pattern c matches values that are equal to c (==)

### Lists

#### Right Associativity

Convention: Operators ending in “:” associate to the right.

    `A :: B :: C` is interpreted as `A :: (B :: C)`

Example

    val numbs = 1 :: 2 :: 3 :: 4 :: Nil 

or

    val numbs = 1 :: (2 :: (3 :: (4 :: Nil))) 

Operators ending in “:” are seen as methods calls of the right-hand operand

    Nil.::(4).::(3).::(2).::(1)


#### List Patterns

It is possible to decompose lists with pattern matching

* `Nil`
* p :: ps
* List(p1,…,pn)

Example

| code           | description                                       |
|----------------|---------------------------------------------------|
| 1 :: 2 :: xs   | List of that stat with 1 and then 2               |
| x :: Nil       | Lists of length 1                                 |
| List(x)        | Same as x :: Nil                                  |
| List()         | The empty list, same as Nil                       |
| List(2 :: xs)  | A list that contains another list starting with 2 |


#### Sorting Lists


