

##  Memory model

![memory model](http://www.pointsoftware.ch/wp-content/uploads/2012/10/JUtH_20121024_RuntimeDataAreas_1_MemoryModel.png)

![heap Method-Area Thread](http://www.pointsoftware.ch/wp-content/uploads/2012/10/JUtH_20121024_RuntimeDataAreas_2_MemoryModel.png)

![memory model2](http://www.pointsoftware.ch/wp-content/uploads/2012/10/JUtH_20121024_RuntimeDataAreas_6_MemoryModel.png)

http://www.pointsoftware.ch/de/under-the-hood-runtime-data-areas-javas-memory-model/


## Collections

Collection implementations in pre-JDK 1.2 versions of the Java platform included few data structure classes, but did not contain a collections framework.[2] The standard methods for grouping Java objects were via the array, the Vector, and the Hashtable classes, which unfortunately were not easy to extend, and did not implement a standard member interface. (wikipedia)


![class- and interface hierarchy of java.util.Map](http://upload.wikimedia.org/wikipedia/commons/1/1c/Map_Classes.jpg)

![collections](http://www.journaldev.com/wp-content/uploads/2013/01/Java-Collections-Framework.png)

### Collection Interfaces and classes

https://thenewcircle.com/static/bookshelf/java_fundamentals_tutorial/generics_collections.html

* List <- ArrayList, LinkedList
* Set <- HashSet, LinkedHashSet, 
* SortedSet <- TreeSet, ConcurrentSkipListSet
* Queue <- LinkedList, PriorityQueue, ArrayBlockingQueue, ConcurrentLinkedQueue, DelayQueue, LinkedBlockingQueue, PriorityBlockingQueue, SynchronousQueue
* Map <- HashMap, LinkedHashMap
* SortedMap <- TreeMap

### Collections Class

The java.util.Collections class (note the final "s" ) consists exclusively of static methods that operate on or return collections.

Features include:
* Taking a collection and returning an unmodifiable view of the collection
* Taking a collection and returning a synchronized view of the collection for thread-safe use
* Returning the minimum or maximum element from a collection
* Sorting, reversing, rotating, and randomly shuffling List elements


## Semantics

## Hash maps

* HashMap: A hash table implementation of the Map interface. The best all-around implementation of the Map interface, providing fast lookup and updates.
* LinkedHashMap: A hash table and linked list implementation of the Map interface. It maintains the insertion order of elements for iteration, and runs nearly as fast as a HashMap.
* TreeMap: A red-black tree implementation of the SortedMap interface, maintaining the collection in sorted order, but slower for lookups and updates.

## Concurrent hash maps

### HashMap Vs HashTable Vs ConcurrentHashmap ?

The Hashtable is among the original collection classes in Java.Hashtable extends the Dictionary class, which as the Javadocs state, is obsolete and has been replaced by the Map interface. HashMap is part of the new Collections Framework, added with Java 2.

The key difference between the two is that access to the Hashtable is synchronized on the table while access to the HashMap is not synchronized.This makes HashMap better for non-threaded applications, as unsynchronized Objects typically perform better than synchronized ones.

HashMap permits null values in it, while Hashtable doesn't.



### ConcurrentHashMap vs Synchronized HashMap ?

Both maps are thread-safe implementations of the Map interface. ConcurrentHashMap is implemented for higher throughput in cases where high concurrency is expected.

So the major advantage of using ConcurrentHashMap is "performance" as the lock is not applied on wholeMap as is the case with a Synchronised access to hashmap or Hashtable.

As we know that hash maps store their data in a series of separate buckets, it is possible to lock only the portion of the map that is being accessed.ConcurrentHashMap uses this to provide us a highly optimized synchronised way of accessing HashMap.ConcurrentHash hash map follows following to provide a concurrent access:

1. Writing to a ConcurrentHashMap locks only a portion of the map
2. Reads can occur without locking.

## Executors

### Executors Example 1

```Java
        ExecutorService es = Executors.newFixedThreadPool(NB_THREADS);
        final PreDealChecker agent = new PreDealCheckerBuilder().setCounterParty(COUNTER_PARTY_FIXTURE)
                .setPreAuthorisedTradingLimit(PRE_AUTHORISATION_FIXTURE)
                .setDailyTradingLimit(DAILY_LIMIT_FIXTURE).createInstance();

        for(int i=0; i<NB_THREADS ;i++) {
            final int threadNb = i;
            es.submit(new Runnable() {
                @Override
                public void run() {
                    for(int j=0 ; j<ITERATIONS ; j++) {
                        agent.handle(COUNTER_PARTY_FIXTURE, 1 + threadNb);
                    }
                }
            });
        }

        es.shutdown();
        es.awaitTermination(1,TimeUnit.DAYS);
```

### Executors Example 2

```java
        ExecutorService executor= Executors.newFixedThreadPool(10);
        Callable task = new Callable<Object>() {

            @Override
            public Object call() throws Exception {
                System.out.println("Hello world");
                return true;

            }
        };

        List<Future<Boolean>> futures = new ArrayList<Future<Boolean>>(10);
        for(int i=0 ;i<10 ; i++) {
            futures.add(executor.submit(task));
        }
        System.out.println("waiting");
        for(int i=0 ;i<10 ; i++) {
            Assert.assertTrue(futures.get(i).get());
        }
        //executor.shutdown();
        //executor.awaitTermination(1,TimeUnit.HOURS);

        System.out.println("bye");
```

## Java concurrent package

### Interfaces

BlockingDeque
BlockingQueue
Callable
CompletionService
ConcurrentMap
ConcurrentNavigableMap
Delayed
Executor
ExecutorService
ForkJoinPool.ForkJoinWorkerThreadFactory
ForkJoinPool.ManagedBlocker
Future
RejectedExecutionHandler
RunnableFuture
RunnableScheduledFuture
ScheduledExecutorService
ScheduledFuture
ThreadFactory
TransferQueue

### Classes

AbstractExecutorService
ArrayBlockingQueue
ConcurrentHashMap
ConcurrentLinkedDeque
ConcurrentLinkedQueue
ConcurrentSkipListMap
ConcurrentSkipListSet
CopyOnWriteArrayList
CopyOnWriteArraySet
CountDownLatch
CyclicBarrier
DelayQueue
Exchanger
ExecutorCompletionService
Executors
ForkJoinPool
ForkJoinTask
ForkJoinWorkerThread
FutureTask
LinkedBlockingDeque
LinkedBlockingQueue
LinkedTransferQueue
Phaser
PriorityBlockingQueue
RecursiveAction
RecursiveTask
ScheduledThreadPoolExecutor
Semaphore
SynchronousQueue
ThreadLocalRandom
ThreadPoolExecutor
ThreadPoolExecutor.AbortPolicy
ThreadPoolExecutor.CallerRunsPolicy
ThreadPoolExecutor.DiscardOldestPolicy
ThreadPoolExecutor.DiscardPolicy

## Blockers

### 1.4 Latch

http://www.javamex.com/tutorials/synchronization_wait_notify_4.shtml

```java

public class Latch {
  private final Object synchObj = new Object();
  private int count;

  public Latch(int noThreads) {
    synchronized (synchObj) {
      this.count = noThreads;
    }
  }
  public void awaitZero() throws InterruptedException {
    synchronized (synchObj) {
      while (count > 0) {
        synchObj.wait();
      }
    }
  }
  public void countDown() {
    synchronized (synchObj) {
      if (--count <= 0) {
        synchObj.notifyAll();
      }
    }
  }
}
```

### CountDownLatch

```java
final CountDownLatch startGate = new CountDownLatch(1);
final CountDownLatch endGate = new CountDownLatch(nThreads);
Thread t = new Thread() {

    public void run() {
        try {
            startGate.await();
            try {
                task.run();
            } finally {
                endGate.countDown();
            }
        } catch (InterruptedException ignored) { }
    }
};
long start = System.nanoTime();
startGate.countDown();
endGate.await();
long end = System.nanoTime();

```

### a BlockingQueue

http://tutorials.jenkov.com/java-concurrency/blocking-queues.html

A blocking queue is a queue that blocks when you try to dequeue from it and the queue is empty, or if you try to enqueue items to it and the queue is already full. A thread trying to dequeue from an empty queue is blocked until some other thread inserts an item into the queue. A thread trying to enqueue an item in a full queue is blocked until some other thread makes space in the queue, either by dequeuing one or more items or clearing the queue completely.

![BlockingQueue](http://tutorials.jenkov.com/images/java-concurrency-utils/blocking-queue.png)

## Interrupting something that is uninterruptible – read operation

```java
final ExecutorService executor = Executors.newSingleThreadExecutor();
final MyCallable myCallable = new MyCallable();
final Future<?> myFuture = executor.submit(myCallable);
myCallable.put("Go Southend United FC!");
myFuture.cancel(true);
executor.shutdown();
```

or in Clojure 

```clojure
(let [task (FutureTask. fn)
      thr (Thread. task)]
  (.start thr)
  ;;wait for signal...
  (.cancel task true)
  (.stop thr))
```


## Reactive Design



## StringBuilder vs StringBuffer ?

`StringBuffer` is synchronized, `StringBuilder` is not.
