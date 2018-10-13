# Cassandra stuff


## Start cassandra

    bin/dse cassandra

## stop cassandra


    bin/dse cassandra stop

## Status

    bin/nodetool status


## Connect with CQL

    bin/cqlsh -u cassandra -p cassandra

    bin/cqlsh -u mathieu -p 'secret'

## Create super user

    CREATE ROLE dba with SUPERUSER = true AND LOGIN = true and PASSWORD = 'supersecret';

or  

    CREATE USER dba WITH PASSWORD 'bacon' SUPERUSER;

## Alter cassandra user passord

    ALTER USER cassandra WITH PASSWORD 'cassandra2';

## Create normal user

    CREATE ROLE mario with SUPERUSER = false AND LOGIN = true and PASSWORD = 'secret';

## List roles:

    list roles;

## Grant DseClientTool RPC

    GRANT EXECUTE ON REMOTE OBJECT DseClientTool TO mathieu;


## Tutorial

### Create Keyspaces

```sql
DROP KEYSPACE IF EXISTS killrvideo;

CREATE KEYSPACE killrvideo WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

use killrvideo;
```

### Create Table

```sql
CREATE TABLE videos (video_id uuid, added_date timestamp, Title text, PRIMARY KEY (video_id ));
```

### Insert into Table

```sql
insert into videos (video_id , added_date , title ) values (1645ea59-14bd-11e5-a993-8138354b7e31, '2014-01-29', 'Cassandra History');
insert into videos (video_id , added_date , title ) values (245e8024-14bd-11e5-9743-8238356b7e32, '2012-04-03', 'Cassandra & SSDs');
insert into videos (video_id , added_date , title ) values (3452f7de-14bd-11e5-855e-8738355b7e3a, '2013-03-17', 'Cassandra Intro');
insert into videos (video_id , added_date , title ) values (4845ed97-14bd-11e5-8a40-8338255b7e33, '2013-10-16', 'DataStax DevCenter');
insert into videos (video_id , added_date , title ) values (5645f8bd-14bd-11e5-af1a-8638355b8e3a, '2013-04-16', 'What is DataStax Enterprise?');
```

### Copy from CSV

```
COPY videos(video_id, added_date, title)
FROM '/home/ubuntu/labwork/data-files/videos.csv'
WITH HEADER=TRUE;
```
### Copy to CSV

```
COPY videos(video_id, added_date, title)
TO '/home/ubuntu/labwork/data-files/videos.csv'
WITH HEADER=TRUE;
```

# DSE and spark

## Intro

https://docs.datastax.com/en/dse/6.0/dse-dev/datastax_enterprise/spark/sparkIntro.html

3 modes:

* DSE with embeded Spark
* DSE + DSE Solo
* DSE + Spark Standalone a.k.a. Bring Your Own Spark (BYOS)

## DSE with embeded Spark

## Starting Spark within DSE

Ref: [Starting Spark within DSE](https://docs.datastax.com/en/dse/6.0/dse-dev/datastax_enterprise/spark/startingSpark.html)

The software components for a single DataStax Enterprise analytics node are:
* Spark Worker
* DataStax Enterprise File System (DSEFS)
* Cassandra File System (CFS), deprecated as of DSE 5.1
* The database

DSE Spark nodes use a different resource manager than standalone Spark nodes. The DSE Resource Manager simplifies integration between Spark and DSE. In a DSE Spark cluster, client applications use the CQL protocol to connect to any DSE node, and that node redirects the request to the Spark Master.

The communication between the Spark client application (or driver) and the Spark Master is secured the same way as connections to DSE, which means that plain password authentication as well as Kerberos authentication is supported, with or without SSL encryption. Encryption and authentication can be configured per application, rather than per cluster. Authentication and encryption between the Spark Master and Worker nodes can be enabled or disabled regardless of the application settings.

Spark Workers and Spark Master are part of the main DSE process. Workers spawn executor JVM processes which do the actual work for a Spark application (or driver). Spark executors use native integration to access data in local transactional nodes through the Spark-Cassandra Connector. The memory settings for the executor JVMs are set by the user submitting the driver to DSE.

The Spark Master High Availability mechanism uses a special table in the spark_system keyspace to store information required to recover Spark workers and the application. Unlike the high availability mechanism mentioned in Spark documentation, DataStax Enterprise does not use ZooKeeper.

If the original Spark Master fails, the reserved one automatically takes over. To find the current Spark Master, run:

In deployment for each Analytics datacenter one node runs the Spark Master, and Spark Workers run on each of the nodes. The Spark Master comes with automatic high availability.

DataStax Enterprise provides [Automatic Spark Master management](https://docs.datastax.com/en/dse/6.0/dse-dev/datastax_enterprise/spark/autoElectSparkMaster.html).

As you run Spark, you can access data in the Hadoop Distributed File System (HDFS), the Cassandra File System (CFS), or the DataStax Enterprise File System (DSEFS) by using the URL for the respective file system.


![Spark with DSE](https://docs.datastax.com/en/dse/6.0/dse-dev/datastax_enterprise/images/sparkCommunication.png)

### Monitoring of Spark DSE
To use the Spark web interface:
Enter the listen IP address of any Spark node in a browser followed by port number 7080. Starting in DSE 5.1, all Spark nodes within an Analytics datacenter will redirect to the current Spark Master.

If the Spark Master is not available, the UI will keep polling for the Spark Master every 10 seconds until the Master is available.

The Spark web interface can be secured using SSL. SSL encryption of the web interface is enabled by default when client encryption is enabled.

ref: [Monitoring](https://docs.datastax.com/en/dse/6.0/dse-dev/datastax_enterprise/spark/sparkWebInterface.html)


## DSE + DSE Solo

DSE Analytics Solo datacenters do not store any database or search data, but are strictly used for analytics processing. They are used in conjunction with one or more datacenters that contain database data.

ref: [Creating a DSE Analytics Solo datacenter](https://docs.datastax.com/en/dse/6.0/dse-dev/datastax_enterprise/spark/dseAnalyticsSolo.html)


## DSE + Spark Standalone a.k.a. Bring Your Own Spark (BYOS)

Ref: https://www.datastax.com/dev/blog/bring-your-own-spark

Bring Your Own Spark (BYOS) is a feature of DSE Analytics designed to connect from external Apache Spark™ systems to DataStax Enterprise with minimal configuration efforts. In this post we introduce how to configure BYOS and show some common use cases.

BYOS extends the DataStax Spark Cassandra Connector with DSE security features such as Kerberos and SSL authentication. It also includes drivers to access the DSE Cassandra File System (CFS) and DSE File System (DSEFS) in 5.1.

There are three parts of the deployment:

<dse_home>clients/dse-byos_2.10-5.0.6.jar is a fat jar. It includes everything you need to connect the DSE cluster: Spark Cassandra Connector with dependencies, DSE security connection implementation, and CFS driver.
'dse client-tool configuration byos-export' tool help to configure external Spark cluster to connect to the DSE
'dse client-tool spark sql-schema' tool generates SparkSQL-compatible scripts to create external tables for all or part of DSE tables in SparkSQL metastore.

HDP 2.3+ and CDH 5.3+ are the only Hadoop distributions which support Java 8 officially and which have been tested with BYOS in DSE 5.0 and 5.1.

![BYOS](https://www.datastax.com/wp-content/uploads/9999/07/fig1.png)

## Open Source Spark Cassandra Connector

https://github.com/datastax/spark-cassandra-connector
