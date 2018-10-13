CREATE KEYSPACE killrvideo
WITH replication = {'class':'SimpleStrategy','replication_factor': 1};
USE killrvideo;


CREATE TABLE videos_by_tag (tag text, video_id uuid, added_date timestamp, Title text,
  PRIMARY KEY ((tag),video_id ));

CREATE TABLE videos_by_tag (tag text, video_id uuid, added_date timestamp, Title text, PRIMARY KEY ((tag),video_id ));



COPY videos_by_tag(tag,video_id, added_date, title)
FROM '~/Dropbox/projects/work-kit/articles/cassandra/dse_course/DS201/videos-by-tag.csv' WITH HEADER=TRUE;

SELECT token(video_id), video_id
FROM videos_by_tag;

select * from videos_by_tag WHERE tag = 'datastax' ;

SELECT *
FROM videos_by_tag
WHERE title = 'Cassandra Intro';

select * from videos_by_tag WHERE title = 'Cassandra Intro' ALLOW FILTERING ;


CREATE TABLE videos_by_tag (
tag text,
video_id uuid,
added_date timestamp,
title text,
PRIMARY KEY ((tag), added_date, video_id)
) WITH CLUSTERING ORDER BY(added_date DESC);

select * from videos_by_tag;


#6) Execute your query again, but list the oldest videos first.
SELECT *
FROM videos_by_tag
ORDER BY added_date ASC;

#NOTE: Your query will fail. When specifying ORDER BY, you must restrict the partition key.
#7) Change your query to restrict the partition key value to 'cassandra'.

SELECT *
FROM videos_by_tag
WHERE tag = 'cassandra'
ORDER BY added_date ASC;

select * from videos_by_tag WHERE tag = 'datastax' ;

# Change your query to retrieve videos made in 2013 or later.
select * from videos_by_tag WHERE tag = 'cassandra' and added_date > '2013-01-01' ;

SELECT *
FROM videos_by_tag
WHERE tag = 'cassandra' and added_date > '2013-01-01'
ORDER BY added_date ASC;

-----

# Driver exercise

from cassandra.cluster import Cluster
from cassandra.auth import PlainTextAuthProvider
Cluster(protocol_version = 3, auth_provider=PlainTextAuthProvider(username='mathieu', password='password'))
session = cluster.connect('killrvideo')


for val in session.execute("SELECT * FROM videos_by_tag"):
  print(val[0])


print('{0:12} {1:40} {2:5}'.format('Tag', 'ID', 'Title'))
for val in session.execute("select * from videos_by_tag"):
  print('{0:12} {1:40} {2:5}'.format(val[0], val[2], val[3]))  

session.execute(
  "INSERT INTO videos_by_tag (tag, added_date, video_id, title)" +
  "VALUES ('cassandra', '2013-01-10', uuid(), 'Cassandra Is My Friend')")

#  7) Now run the following code to view your new record:
print('{0:12} {1:40} {2:5}'.format('Tag', 'ID', 'Title'))
for val in session.execute("select * from videos_by_tag"):
  print('{0:12} {1:40} {2:5}'.format(val[0], val[2], val[3]))
