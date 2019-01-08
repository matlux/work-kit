
## Always Implement Immutable Class


### Anti-pattern -

```java
class MutableMath {
    int factorial=1;
    int calculateFactorial(int n) {
        int acc=1;
        while(acc <= n){
            factorial = factorial*acc;
            acc++;
        }
        return factorial;
    }
}
MutableMath m1 = new MutableMath();
;
System.out.println("m1=" + m1.calculateFactorial(5));  // 120
System.out.println("m1=" + m1.calculateFactorial(5));  // 14400
```

### Anti-pattern2 - Always Implement Indempotent methods

```java
class MutableMath {
    int factorial=1;
    int n=1;
    void set(int n) {
        this.n = n;
    }

    void calculateFactorial() {
        int acc=1;
        while(acc <= n){
            factorial = factorial*acc;
            acc++;
        }
    }
    int getFactorial() {
        return factorial;
    }

}
MutableMath m3 = new MutableMath();
m3.set(5);
m3.calculateFactorial();

System.out.println("m3=" + m3.getFactorial());  // 120
m3.calculateFactorial();
System.out.println("m3=" + m3.getFactorial());  // 14400
```



### Immutable class

```java
class ImmutableMath {
    int calculateFactorial(int n) {
        return IntStream.range(1, n+1).reduce(Math::multiplyExact).getAsInt();
    }
}
ImmutableMath m2 = new ImmutableMath();
System.out.println("m2=" + m2.calculateFactorial(5));  // 120
System.out.println("m2=" + m2.calculateFactorial(5));  // 120

```


## Ifs are not funtionaly pure and introduce mutability


### Anti-pattern

```java
```


### Immutable class

```java
```


## Use reduce over loops when agregations


### Anti-pattern

```java
while(acc <= n){
    factorial = factorial*acc;
    acc++;
}
```


### reduce

```java
IntStream.range(1, n+1).reduce(Math::multiplyExact).getAsInt();
```



## Use map over loops when processing a list of values - Stream API vs good old java < 7 style

ref: https://www.toptal.com/java/why-you-need-to-upgrade-to-java-8-already

Lets start with a short example. We will use this data model in all examples:

```java
  class Author {
     String name;
     int countOfBooks;
  }

  class Book {
     String name;
     int year;
     Author author;
  }
```

### java < 7 style (avoid!!)

```java
List<String> allNames = new ArrayList();
for (Book book : books) {
    if (book.author != null && book.year > 2005){
        allNames.add(book.author.name);
    }
}
```


### Java8 - from Stream (better)

```java
List<String> allNames = books.stream().filter(book-> book.author != null && book.year > 2005)
        .map(book -> book.author.name).collect(Collectors.toList());
```

## List litteral initialisation

ref: https://www.baeldung.com/java-init-list-one-line

### java < 7 style (avoid!!)

```java
// good old java < 7 style. Verbose and mutable/stateful
List<String> java6list = new ArrayList();
java6list.add("foo"); // mutating state is bad
java6list.add("bar"); // mutating state is bad
```

### Java8 - from Stream (better)

A one liner litteral initialisation. Unfortunatelly this is mutable.

```java
// a one liner litteral initialisation. Unfortunatelly this is mutable
List<String> list = Stream.of("foo", "bar")
      .collect(Collectors.toList());
```

A one liner litteral initialisation. This is clean and bonus this is immutable.

### Java8 - immutable list (Best)

```java
// a one liner litteral initialisation. This is clean and bonus this is immutable
List<String> java8list = Arrays.asList("foo", "bar");
java8list.add("baz"); // calling add will raise a UnsupportedOperationException
```


## Map initialisation

ref: https://www.baeldung.com/java-initialize-hashmap

### java < 7 style

```java
// good old java < 7 style.
Map<String, String> java6Map = new HashMap<>();
java6Map.put("Title","My New Article");
java6Map.put("Title2","Second Article");
```

### Java8 - from Stream (better)

A one liner litteral initialisation. Unfortunatelly this is mutable.

```java
Map<String, Integer> map = Stream.of(
  new AbstractMap.SimpleEntry<>("idea", 1),
  new AbstractMap.SimpleEntry<>("mobile", 2))
  .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
```

### Java8 - from Stream - Immutable List (better but apprently a bit slower)

This is immutable. Baeldung claims the initialisation is somewhat slow. I wouldn't worry about it until one runs some benchmark to see the cost or if this is used in a tight loop.

```java
Map<String, String> map = Stream.of(new String[][] {
    { "Hello", "World" },
    { "John", "Doe" },
}).collect(Collectors.collectingAndThen(
    Collectors.toMap(data -> data[0], data -> data[1]),
    Collections::<String, String> unmodifiableMap));
```


### Guava - immutable (Best)

A one liner litteral initialisation. This is clean and bonus this is immutable. Don't even need Java8.

```java
Map<String, String> guavaMap
  = ImmutableMap.of("Title", "My New Article", "Title2", "Second Article");
```


## Java IO improvements

ref: https://www.journaldev.com/2389/java-8-features-with-examples

Some IO improvements known to me are:

`Files.list(Path dir)` that returns a lazily populated Stream, the elements of which are the entries in the directory.
`Files.lines(Path path)` that reads all lines from a file as a Stream.
`Files.find()` that returns a Stream that is lazily populated with Path by searching for files in a file tree rooted at a given starting file.
`BufferedReader.lines()` that return a Stream, the elements of which are lines read from this BufferedReader.
