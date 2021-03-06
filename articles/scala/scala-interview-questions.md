
## What is the difference between Nil, Null, None, Nothing ?

Nil = empty list
None = absence of element in Option
Null = null exists in all JVM languages, including Scala and Clojure. It's the NPE.
Nothing is a subtype of every other type (including scala.Null); there exist no instances of this type. Although type Nothing is uninhabited, it is nevertheless useful in several ways. For instance, the Scala library defines a value scala.collection.immutable.Nil of type List[Nothing]. Because lists are covariant in Scala, this makes scala.collection.immutable.Nil an instance of List[T], for any element of type T.

## Usages of Null / Nothing / Unit

You only use Nothing if the method never returns (meaning it cannot complete normally by returning, it could throw an exception). Nothing is never instantiated and is there for the benefit of the type system (to quote James Iry: "The reason Scala has a bottom type is tied to its ability to express variance in type parameters.").

One other use of Nothing is as a return type for methods that never return. It makes sense if you think about it. If a method’s return type is Nothing, and there exists absolutely no instance of Nothing, then such a method must never return.

Unit is a subtype of scala.AnyVal. There is only one value of type Unit, (), and it is not represented by any object in the underlying runtime system. A method with return type Unit is analogous to a Java method which is declared void.

Null is a trait and its only instance is null. Null might be used as a bottom type for any "value" ("value" as in "val" or "var", I should say a reference to avoid refering to a primitive JVM value) that is nullable i.e. AnyRef.

When a method takes a Null argument, then we can only pass it a Null reference or null directly, but not any other reference, even if it is null (nullString: String = null for exemple).

## Write code to

* construct a list of the numbers 1 to 10 "list a"
* from "list a" create another list with all those values mutliplied by 3 => "list b"
* then create a list only containing the even numbers from "list b"

### Same question with a for comprehension?

## Extract a list of the vlues from

```scala
val opts = List(None, Some(1), None)
```

Hint:
* with flatMap
* with for comprehension


## How do scala traits differ from java interfaces?
Unlike Java interfaces, Scala traits can include code, which effectively gives the ability to do multiple inheritance.


## what are the differences between an abstract class and trait
Abstract classes can have constructor parameters as well as type parameters. Traits can have only type parameters. There was some discussion that in future even traits can have constructor parameters
Abstract classes are fully interoperable with Java. You can call them from Java code without any wrappers. Traits are fully interoperable only if they do not contain any implementation code

## What is the difference between a Class and a Case Class?
Case classes can be seen as plain and immutable data-holding objects that should exclusively depend on their constructor arguments.

This functional concept allows us to:

* use a compact initialisation syntax `(Node(1, Leaf(2), None)))`
* decompose them using pattern matching
* have equality comparisons implicitly defined
* In combination with inheritance, case classes are used to mimic algebraic datatypes.

If an object performs statefull computations on the inside or exhibits other kinds of complex behaviour, it should be an ordinary class.

## How to convert a method into a function?
	methodName _


## how to implement function?

```scala
class test {
    //anonymous/lambda expression
    (x:Int) => x+3           

    def m1(x:Int) = x+3
    //without_type_inference
    def m1b(x:Int) : Int = x+3
    val f1 = (x:Int) => x+3
    //without_type_inference
    val f1b : (Int) => Int = (x:Int) => x+3
    //with Function1
    val f1c = new Function1[Int,Int]{
    	def apply(x:Int) = x+3
    }
    //without_type_inference
    val f1d : (Int) => Int = new Function1[Int,Int]{
    	def apply(x:Int) = x+3

    class myfunc extends Function1[Int,Int] {
          def apply(x:Int) = x+3
    }
    val f1e = new myfunc
    
    // method to function conversion
    val f2 = m1 _

}
```

## how do you use class name as a function or How to not use `new` when instantiating a class?
	use companion object and implement apply function

## how do you use pattern matching with classes?
	use unapply
	
- See more at: http://danielwestheide.com/blog/2012/11/21/the-neophytes-guide-to-scala-part-1-extractors.html

## how do you use a class as a Function?
	use apply method

for example in a case of a (good old mutable) class:

```scala
class Fraction(var numerator:Int, var denominator:Int){
	 
	  def *(fraction2:Fraction) = {
	 
	    Fraction(this.numerator*fraction2.numerator,
	             this.denominator*fraction2.denominator)
	  }
	 
	  override def toString = this.numerator+"/"+this.denominator
	}
	 
//the below construct is the companion object for Fraction class.
object Fraction{
	 
	  def apply(numer:Int, denom:Int) = new Fraction(numer,denom)
	 
	  def unapply(fraction:Fraction) = {
	    if ( fraction == null ) None
	    else Some(fraction.numerator, fraction.denominator)
	  }
	 
	}

val fract1 = Fraction(3,4)
val fract2 = Fraction(2,4)
val Fraction(numer, denom) = fract1 * fract2
println("Numerator: "+numer+" Denominator: "+denom)

```
- See more at: http://www.javabeat.net/using-apply-unapply-methods-scala/#sthash.SgMIPz0w.dpuf



## What is the different between a list and vector?
Vector are implemented with tries
List are implemented with cons’ed sequences


## How do you use option?
	flatmap or for comprehension

## Why can are List[String] a List[Any] when immutable but not when mutable? (also explain covariance of mutable vs immutable types)
It’s dues to type covariance and contravariance. (see [stackoverflow](http://stackoverflow.com/questions/663254/scala-covariance-contravariance-question) )

Generically, a covariant type parameter is one which is allowed to vary down as the class is subtyped (alternatively, vary with subtyping, hence the "co-" prefix). More concretely:

```scala
trait List[+A]
```

`List[Int]` is a subtype of `List[AnyVal]` because Int is a subtype of AnyVal. This means that you may provide an instance of List[Int] when a value of type `List[AnyVal]` is expected. This is really a very intuitive way for generics to work, but it turns out that it is unsound (breaks the type system) when used in the presence of mutable data. This is why generics are invariant in Java. Brief example of unsoundness using Java arrays (which are erroneously covariant):

```java
Object[] arr = new Integer[1];
arr[0] = "Hello, there!";
```

We just assigned a value of type String to an array of type Integer[]. For reasons which should be obvious, this is bad news. Java's type system actually allows this at compile time. The JVM will "helpfully" throw an ArrayStoreException at runtime. Scala's type system prevents this problem because the type parameter on the Array class is invariant (declaration is [A] rather than [+A]).

Note that there is another type of variance known as contravariance. This is very important as it explains why covariance can cause some issues. Contravariance is literally the opposite of covariance: parameters vary upward with subtyping. It is a lot less common partially because it is so counter-intuitive, though it does have one very important application: functions.

```scala
trait Function1[-P, +R] {
  def apply(p: P): R
}
```

Notice the "-" variance annotation on the P type parameter. This declaration as a whole means that Function1 is contravariant in P and covariant in R. Thus, we can derive the following axioms:

```scala
T1' <: T1
T2 <: T2'
---------------------------------------- S-Fun
Function1[T1, T2] <: Function1[T1', T2']
```

Notice that T1' must be a subtype (or the same type) of T1, whereas it is the opposite for T2 and T2'. In English, this can be read as the following:

A function A is a subtype of another function B if the parameter type of A is a supertype of the parameter type of B while the return type of A is a subtype of the return type of B.

The reason for this rule is left as an exercise to the reader (hint: think about different cases as functions are subtyped, like my array example from above).

With your new-found knowledge of co- and contravariance, you should be able to see why the following example will not compile:

```scala
trait List[+A] {
  def cons(hd: A): List[A]
}
```

The problem is that A is covariant, while the cons function expects its type parameter to be contravariant. Thus, A is varying the wrong direction. Interestingly enough, we could solve this problem by making List contravariant in A, but then the return type List[A] would be invalid as the cons function expects its return type to be covariant.

Our only two options here are to a) make A invariant, losing the nice, intuitive sub-typing properties of covariance, or b) add a local type parameter to the cons method which defines A as a lower bound:

```scala
def cons[B >: A](v: B): List[B]
```

This is now valid. You can imagine that A is varying downward, but B is able to vary upward with respect to A since A is its lower-bound. With this method declaration, we can have A be covariant and everything works out.

Notice that this trick only works if we return an instance of List which is specialized on the less-specific type B. If you try to make List mutable, things break down since you end up trying to assign values of type B to a variable of type A, which is disallowed by the compiler. Whenever you have mutability, you need to have a mutator of some sort, which requires a method parameter of a certain type, which (together with the accessor) implies invariance. Covariance works with immutable data since the only possible operation is an accessor, which may be given a covariant return type.

