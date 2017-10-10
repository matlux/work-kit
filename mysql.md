# MYSQL


https://www.webyog.com/


## How to dump a database

    mysqldump -h myhost.eu-west-1.rds.amazonaws.com -uroot -pmypassword  --databases MyDatabaseName1 MyDatabaseName2 MyDatabaseName3 > mysqldump.sql

## How to import a dump

    mysql -h myhost -u root -p password < dump.sql
