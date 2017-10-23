

## HDFS

    hadoop fs -rm -r /user/<username>/backup
    hadoop fs -mkdir /user/<username>/backup
    hadoop fs -put ~/kvstore_backup/ /user/<username>/

## Hbase

### References

[https://learnhbase.wordpress.com/2013/03/02/hbase-shell-commands/](https://learnhbase.wordpress.com/2013/03/02/hbase-shell-commands/)

[http://www.tutorialspoint.com/hbase/hbase_scan.htm](http://www.tutorialspoint.com/hbase/hbase_scan.htm)

### Import / Export

```shell
bin/hbase org.apache.hadoop.hbase.mapreduce.Export \
   <tablename> <outputdir> [<versions> [<starttime> [<endtime>]]]
```

```sh
bin/hbase org.apache.hadoop.hbase.mapreduce.Import <tablename> <inputdir>   
```

### list keys
    count 'table_name', INTERVAL=> 1
    
### Create table
    
    create 'mynamespace:table_name', {NAME => 'main', VERSIONS => 100}

### List content of a table

    scan 'mynamespace:table_name'
    scan 'mynamespace:table_name', {STARTROW => "\x00\x00global", LIMIT => 10}
    scan 'mynamespace:table_name', {STARTROW => "\x00\x00home", ENDROW => "\x00\x00home\x00\x01"}

### Add an entry to a table

    put 'mynamespace:table_name', "\x00\x00config\x00\x00users\x00\x00phil", "main:content", '{"rights" (("root" "p") ("testgroup" "p")), "groups" ()}'

### Import a directory into hbase

```ruby
  Dir["**/*"].select { |path| /[0-9]*.dsl/ =~ path }.collect { |nb_path|
    /(.*\/[0-9]*).dsl/.match(nb_path)
    m = $1
    /(.*).dsl/.match(nb_path)
    m = $1 if m.nil?
    key = "\x00\x00"+ m.gsub(/\//,"\x00x00")
    [key, nb_path]
  }.each { | (key, nb_path) |
    content = IO.read(nb_path)
    put 'mynamespace:table_name', key , "mycolumnFam:myQualifier", content
  }
```

### Delete scanned rows

```ruby
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.HTable
import org.apache.hadoop.hbase.client.Scan
import org.apache.hadoop.hbase.client.ResultScanner
import org.apache.hadoop.hbase.client.Result
import org.apache.hadoop.hbase.client.ConnectionFactory
import org.apache.hadoop.hbase.client.Connection
import org.apache.hadoop.hbase.client.Delete
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.filter.KeyOnlyFilter
import java.util.ArrayList

  connection = ConnectionFactory.createConnection(HBaseConfiguration.create())
  admin = connection.getAdmin()
  tableName = TableName.valueOf("my_ns:MyTable")
  table = connection.getTable(tableName)
  scanOp = Scan.new(Bytes.toBytes("\x00\x00home"), Bytes.toBytes("\x00\x00home\x00\x01"))
  scanOp.setFilter(KeyOnlyFilter.new)
  rs = table.getScanner(scanOp)
  
  output = ArrayList.new
  rs.each { |r| 
    r.raw.each { |kv|
      row = Bytes.toString(kv.getRow)
      fam = Bytes.toString(kv.getFamily)
      ql = Bytes.toString(kv.getQualifier)
      ts = kv.getTimestamp
      output.add [kv.getRow, row, fam, ql, ts]
    }
  }
  keys2delete = output.to_a.collect {|(row, rowStr, fam, ql, ts)| row} 
  
  keys2delete.each{|row|
    table.delete(Delete.new(row))
  }
```

## Yarn

### Web UI

    http://localhost:8088
