



hadoop fs -rm -r /user/<username>/backup
hadoop fs -mkdir /user/<username>/backup
hadoop fs -put ~/kvstore_backup/ /user/<username>/


bin/hbase org.apache.hadoop.hbase.mapreduce.Export \
   <tablename> <outputdir> [<versions> [<starttime> [<endtime>]]]
   
bin/hbase org.apache.hadoop.hbase.mapreduce.Import <tablename> <inputdir>   

