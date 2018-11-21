# What is Spark

Spark is a General-purpose distributed data processing engine. It contains a few libs like, SQL, Streaming, Machine learning. and Graph processing.

It is available for Java, Scala, Python and R.

Tasks most frequently associated with Spark include ETL and SQL batch jobs across large data sets.

## What is Distributed Computing

* A Distributed System is a collection of processes distributed across multiple hosts working on a common goal.

## MapReduce

Ref: https://mapr.com/blog/spark-101-what-it-what-it-does-and-why-it-matters/

Before Spark, there was MapReduce, a resilient distributed processing framework, which enabled Google to index the exploding volume of content on the web, across large clusters of commodity servers.
3 core concepts to the Google strategy:

Distribute data: when a data file is uploaded into the cluster, it is split into chunks, called data blocks, and distributed amongst the data nodes and replicated across the cluster.
Distribute computation: users specify a map function that processes a key/value pair to generate a set of intermediate key/value pairs and a reduce function that merges all intermediate values associated with the same intermediate key. Programs written in this functional style are automatically parallelized and executed on a large cluster of commodity machines in the following way:
The mapping process runs on each assigned data node, working only on its block of data from a distributed file.
The results from the mapping processes are sent to the reducers in a process called "shuffle and sort": key/value pairs from the mappers are sorted by key, partitioned by the number of reducers, and then sent across the network and written to key sorted "sequence files" on the reducer nodes.
The reducer process executes on its assigned node and works only on its subset of the data (its sequence file). The output from the reducer process is written to an output file.
Tolerate faults: both data and computation can tolerate failures by failing over to another node for data or processing.

### Origin of Hadoop and MapReduce

According to its co-founders, Doug Cutting and Mike Cafarella, the genesis of Hadoop was the "Google File System" paper that was published in October 2003. This paper spawned another one from Google – "MapReduce: Simplified Data Processing on Large Clusters". Development started on the Apache Nutch project, but was moved to the new Hadoop subproject in January 2006.[18] Doug Cutting, who was working at Yahoo! at the time, named it after his son's toy elephant. The initial code that was factored out of Nutch consisted of about 5,000 lines of code for HDFS and about 6,000 lines of code for MapReduce.

ref:  https://en.wikipedia.org/wiki/Apache_Hadoop
https://en.wikipedia.org/wiki/Google_File_System
https://en.wikipedia.org/wiki/MapReduce
http://static.googleusercontent.com/media/research.google.com/es/us/archive/mapreduce-osdi04.pdf
https://storage.googleapis.com/pub-tools-public-publication-data/pdf/035fc972c796d33122033a0614bc94cff1527999.pdf

## RDDs

The famous white paper behind Spark:
https://www.usenix.org/system/files/conference/nsdi12/nsdi12-final138.pdf
Authored by M. Zaharia, M. Chowdhury, T. Das, A. Dave, J. Ma, M. McCauley, M.J. Franklin, S. Shenker, I. Stoica.
NSDI 2012. Best Paper Award and Honorable Mention for Community Award.
