# Java cheat cheat


## Debug parameters

    -Xdebug -Xrunjdwp:transport=dt_socket,address=1000,server=y,suspend=n
    -Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000

## How to display jar manifest from jar file

    unzip -p jdiff.jar META-INF/MANIFEST.MF

## How to md5sum a file within a war or a jar file

    unzip -p target/calculation-engine-services.war WEB-INF/lib/calculation-engine-services-0.1-SNAPSHOT.jar | md5sum

## How to extract exception stack

    	            StackTraceElement[] stackTrace = ex.getStackTrace();
	            for (StackTraceElement stackTraceElement : stackTrace) {
					LOG.error(">> " + stackTraceElement.toString());
				}

## Usefull Snippets

### Display Stack

    StringBuilder stack = new StringBuilder();
    for (StackTraceElement st :Thread.currentThread().getStackTrace()) {
    	stack.append(st.toString()).append("\n");
    }
    LOG.info("stack:\n " + stack.toString());

### Harmcrest quick example

```java
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.isIn;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.IsCollectionContaining.hasItem;


assertThat(result, is(expectedResult));
assertThat(nbNodes, is(greaterThan(1)));
assertThat(result, is(notNullValue()));
assertThat(item, isIn(collection));
assertThat(collection, hasItem(item));


```

### Mockito quick example

```java
import org.mockito.Mockito;

MyClass mock = Mockito.mock(MyClass.class);
Mockito.stub(mock.getMethod()).toReturn(i);
```

### Executor quick example


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
