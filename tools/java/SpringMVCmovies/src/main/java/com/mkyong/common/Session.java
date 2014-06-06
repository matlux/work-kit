package com.mkyong.common;


public interface Session {
	
	void registerTask(String taskId, String submitter, String requestId);
	
	String getTaskId();
	
	String getSubmitterId();
	
	String getRequestId();
	
	void endTask();
	
	

}
