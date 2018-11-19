# Spark


## How to start Spark master and working on windows


The launch scripts located at %SPARK_HOME%\sbin do not support Windows. You need to manually run the master and worker as outlined below.

Go to %SPARK_HOME%\bin folder in a command prompt


Run 
```
# to run the master. This will give you a URL of the form spark://ip:port
spark-class org.apache.spark.deploy.master.Master
#to run the worker. Make sure you use the URL you obtained in step 2.
spark-class org.apache.spark.deploy.worker.Worker spark://ip:port
# to connect an application to the newly created cluster.
spark-shell --master spark://ip:port 
```

Ref: https://stackoverflow.com/questions/36593446/failed-to-start-master-for-spark-in-windows
