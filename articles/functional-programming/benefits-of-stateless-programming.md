


Mutability is bad. You should avoid state.





## Why is mutability such a source problems ?


## Benefit of Immutability

ref: http://www.javapractices.com/topic/TopicAction.do?Id=29

Immutable objects greatly simplify your program, since they:

are simple to construct, test, and use
are automatically thread-safe and have no synchronization issues
don't need a copy constructor
don't need an implementation of clone
allow hashCode to use lazy initialization, and to cache its return value
don't need to be copied defensively when used as a field
make good Map keys and Set elements (these objects must not change state while in the collection)
have their class invariant established once upon construction, and it never needs to be checked again
always have "failure atomicity" (a term used by Joshua Bloch): if an immutable object throws an exception, it's never left in an undesirable or indeterminate state


Immutable objects have a very compelling list of positive qualities. Without question, they are among the simplest and most robust kinds of classes you can possibly build. When you create immutable classes, entire categories of problems simply disappear.

Make a class immutable by following these guidelines:

ensure the class cannot be overridden - make the class final, or use static factories and keep constructors private
make fields private and final
force callers to construct an object completely in a single step, instead of using a no-argument constructor combined with subsequent calls to setXXX methods (that is, avoid the Java Beans convention)
do not provide any methods which can change the state of the object in any way - not just setXXX methods, but any method which can change state
if the class has any mutable object fields, then they must be defensively copied when they pass between the class and its caller
In Effective Java, Joshua Bloch makes this compelling recommendation :
"Classes should be immutable unless there's a very good reason to make them mutable....If a class cannot be made immutable, limit its mutability as much as possible."

### Avoid Mutability at all cost

Avoid mutability at all cost unless there is a clear and practical reason to do. Even then consider avoiding it.


### Isolate state

When state is introduced it should be minimal and isolated in a way which makes it easier to keep it consistent. For example using transctional memory like Atoms.


### Referential Transparency

ref: https://en.wikipedia.org/wiki/Referential_transparency

Arithmetic operations are referentially transparent: 5 * 5 can be replaced by 25, for instance. In fact, all functions in the mathematical sense are referentially transparent: sin(x) is transparent, since it will always give the same result for each particular x.

Assignments are not transparent. For instance, the C expression `x = x + 1` changes the value assigned to the variable `x`. Assuming x initially has value 10, two consecutive evaluations of the expression yield, respectively, 11 and 12. Clearly, replacing `x = x + 1` with either 11 or 12 gives a program with different meaning, and so the expression is not referentially transparent. However, calling a function such as int plusone(int x) {return x+1;} is transparent, as it will not implicitly change the input x and thus has no such side effects.

today() is not transparent, as if you evaluate it and replace it by its value (say, "Jan 1, 2001"), you don't get the same result as you will if you run it tomorrow. This is because it depends on a state (the date).

#### Reasoning about the code

#### Tracking bugs

### Value Semantic

### Thread Safety

### Testing
