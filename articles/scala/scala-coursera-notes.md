

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

