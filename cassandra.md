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
