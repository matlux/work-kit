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

