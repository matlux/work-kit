

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

