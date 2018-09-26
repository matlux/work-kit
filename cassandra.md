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
CREATE TABLE videos_by_tag (tag text, video_id uuid, added_date timestamp, Title text, PRIMARY KEY ((tag),video_id ));
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
## Describe a table

    DESCRIBE TABLE videos;
    
    

