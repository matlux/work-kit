
org.apache.cassandra.transport.Server



Message.Dispatcher or the CustomTThreadPoolServer

QueryMessage extends Message.Request

ParsedStatement
SelectStatement implements CQLStatement

CQLStatement
   checkAccess
   validate
   execute

QueryProcessor or the CassandraServer and then the QueryProcessor


The SelectStatement -> ReadCommand

StorageProxy is the workhorse of the Dynamo layer

StoringService

RangeSliceResponseResolver
ReadCallback<RangeSliceReply, Iterable<Row>> handler



ColumnFamily (which represents a single row, not a table of data)
ColumnFamily(users [:false:0@1415137479246663,birth_year:false:8@1415137479246663,gender:false:4@1415137479246663,password:false:9@1415137479246663,session_token:false:5@1415137479246663,state:false:2@1415137479246663,])
	columnName:static?:
  is made of an array of Cell(s)

A Cell is a column name with a value


Estimate Result Rows Per Range:

Daemon Thread [SharedPool-Worker-1] (Suspended (breakpoint at line 1471 in StorageProxy))	
	StorageProxy.estimateResultRowsPerRange(AbstractRangeCommand, Keyspace) line: 1471	
	StorageProxy.getRangeSlice(AbstractRangeCommand, ConsistencyLevel) line: 1527	
	RangeSliceQueryPager.queryNextPage(int, ConsistencyLevel, boolean) line: 89	
	RangeSliceQueryPager(AbstractQueryPager).fetchPage(int) line: 87	
	RangeSliceQueryPager.fetchPage(int) line: 1	
	SelectStatement.execute(QueryState, QueryOptions) line: 227	
	SelectStatement.execute(QueryState, QueryOptions) line: 1	
	QueryProcessor.processStatement(CQLStatement, QueryState, QueryOptions) line: 226	
	QueryProcessor.process(String, QueryState, QueryOptions) line: 248	
	QueryMessage.execute(QueryState) line: 124	
	Message$Dispatcher.channelRead0(ChannelHandlerContext, Message$Request) line: 439	
	Message$Dispatcher.channelRead0(ChannelHandlerContext, Object) line: 1	
	Message$Dispatcher(SimpleChannelInboundHandler<I>).channelRead(ChannelHandlerContext, Object) line: 105	
	DefaultChannelHandlerContext(AbstractChannelHandlerContext).invokeChannelRead(Object) line: 333	
	AbstractChannelHandlerContext.access$700(AbstractChannelHandlerContext, Object) line: 32	
	AbstractChannelHandlerContext$8.run() line: 324	
	Executors$RunnableAdapter<T>.call() line: 471	
	AbstractTracingAwareExecutorService$FutureTask<T>.run() line: 164	
	SEPWorker.run() line: 105	
	Thread.run() line: 724	

Submit PageRangeCommand to StageManager
Daemon Thread [SharedPool-Worker-1] (Suspended)	
	StorageProxy.getRangeSlice(AbstractRangeCommand, ConsistencyLevel) line: 1611	
	RangeSliceQueryPager.queryNextPage(int, ConsistencyLevel, boolean) line: 89	
	RangeSliceQueryPager(AbstractQueryPager).fetchPage(int) line: 87	
	RangeSliceQueryPager.fetchPage(int) line: 1	
	SelectStatement.execute(QueryState, QueryOptions) line: 227	
	SelectStatement.execute(QueryState, QueryOptions) line: 1	
	QueryProcessor.processStatement(CQLStatement, QueryState, QueryOptions) line: 226	
	QueryProcessor.process(String, QueryState, QueryOptions) line: 248	
	QueryMessage.execute(QueryState) line: 124	
	Message$Dispatcher.channelRead0(ChannelHandlerContext, Message$Request) line: 439	
	Message$Dispatcher.channelRead0(ChannelHandlerContext, Object) line: 1	
	Message$Dispatcher(SimpleChannelInboundHandler<I>).channelRead(ChannelHandlerContext, Object) line: 105	
	DefaultChannelHandlerContext(AbstractChannelHandlerContext).invokeChannelRead(Object) line: 333	
	AbstractChannelHandlerContext.access$700(AbstractChannelHandlerContext, Object) line: 32	
	AbstractChannelHandlerContext$8.run() line: 324	
	Executors$RunnableAdapter<T>.call() line: 471	
	AbstractTracingAwareExecutorService$FutureTask<T>.run() line: 164	
	SEPWorker.run() line: 105	
	Thread.run() line: 724	

Executing PageRangeCommand
Daemon Thread [SharedPool-Worker-2] (Suspended (breakpoint at line 110 in PagedRangeCommand))	
	PagedRangeCommand.executeLocally() line: 110	
	StorageProxy$LocalRangeSliceRunnable.runMayThrow() line: 1419	
	StorageProxy$LocalRangeSliceRunnable(StorageProxy$DroppableRunnable).run() line: 2081	
	Executors$RunnableAdapter<T>.call() line: 471	
	AbstractTracingAwareExecutorService$FutureTask<T>.run() line: 164	
	SEPWorker.run() line: 105	
	Thread.run() line: 724	


Executing PageRangeCommand with secondary index:
Daemon Thread [SharedPool-Worker-2] (Suspended (breakpoint at line 68 in CompositesSearcher))	
	CompositesSearcher.search(ExtendedFilter) line: 68	
	SecondaryIndexManager.search(ExtendedFilter) line: 626	
	ColumnFamilyStore.search(ExtendedFilter) line: 2065	
	PagedRangeCommand.executeLocally() line: 114	
	StorageProxy$LocalRangeSliceRunnable.runMayThrow() line: 1419	
	StorageProxy$LocalRangeSliceRunnable(StorageProxy$DroppableRunnable).run() line: 2081	
	Executors$RunnableAdapter<T>.call() line: 471	
	AbstractTracingAwareExecutorService$FutureTask<T>.run() line: 164	
	SEPWorker.run() line: 105	
	Thread.run() line: 724	





CompactionExecutor:

Daemon Thread [CompactionExecutor:4] (Suspended (breakpoint at line 194 in CollationController))	
	CollationController.collectAllData(boolean) line: 194	
	CollationController.getTopLevelColumns(boolean) line: 63	
	ColumnFamilyStore.getTopLevelColumns(QueryFilter, int) line: 1873	
	ColumnFamilyStore.getColumnFamily(QueryFilter) line: 1681	
	Keyspace.getRow(QueryFilter) line: 345	
	SliceFromReadCommand.getRow(Keyspace) line: 59	
	SelectStatement.readLocally(String, List<ReadCommand>) line: 299	
	SelectStatement.executeInternal(QueryState, QueryOptions) line: 311	
	SelectStatement.executeInternal(QueryState, QueryOptions) line: 1	
	QueryProcessor.executeInternal(String, Object...) line: 305	
	SystemKeyspace.getSSTableReadMeter(String, String, int) line: 909	
	SSTableReader.<init>(Descriptor, Set<Component>, CFMetaData, IPartitioner, long, StatsMetadata, OpenReason) line: 519	
	SSTableReader.<init>(Descriptor, Set<Component>, CFMetaData, IPartitioner, SegmentedFile, SegmentedFile, IndexSummary, IFilter, long, StatsMetadata, OpenReason) line: 546	
	SSTableReader.internalOpen(Descriptor, Set<Component>, CFMetaData, IPartitioner, SegmentedFile, SegmentedFile, IndexSummary, IFilter, long, StatsMetadata, OpenReason) line: 481	
	SSTableWriter.closeAndOpenReader(long, long) line: 441	
	SSTableWriter.closeAndOpenReader(long) line: 429	
	SSTableRewriter.finish(boolean, long) line: 314	
	SSTableRewriter.finish(boolean) line: 307	
	CompactionTask.runWith(File) line: 200	
	CompactionTask(DiskAwareRunnable).runMayThrow() line: 48	
	CompactionTask(WrappedRunnable).run() line: 28	
	CompactionTask.executeInternal(CompactionManager$CompactionExecutorStatsCollector) line: 75	
	CompactionTask(AbstractCompactionTask).execute(CompactionManager$CompactionExecutorStatsCollector) line: 59	
	CompactionManager$BackgroundCompactionTask.run() line: 232	
	Executors$RunnableAdapter<T>.call() line: 471	
	FutureTask<V>.run() line: 262	
	CompactionManager$CompactionExecutor(ThreadPoolExecutor).runWorker(ThreadPoolExecutor$Worker) line: 1145	
	ThreadPoolExecutor$Worker.run() line: 615	
	Thread.run() line: 724	

