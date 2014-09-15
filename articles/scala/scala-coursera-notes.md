

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