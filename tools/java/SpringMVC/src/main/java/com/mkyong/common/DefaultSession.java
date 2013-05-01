package com.mkyong.common;


public class DefaultSession implements Session{
	
	private String taskId;
	private String submitterId;
	private String requestId;
	private String id;

    public DefaultSession() {
        this("default Constructor");
    }
    public DefaultSession(String id) {
        this.id = id;
    }

    @Override
	public void registerTask(String taskId, String submitterId, String requestId ) {
		this.taskId = taskId;
		this.submitterId = submitterId;
		this.requestId = requestId;
	}

	@Override
	public String getTaskId() {
		return taskId;
	}
	
	@Override
	public String getSubmitterId() {
		return submitterId;
	}
	
	@Override
	public String getRequestId() {
		return requestId;
	}
	
	@Override
	public void endTask() {
		taskId = null;
		submitterId = null;
		requestId = null;
	}

    @Override
    public String toString() {
        return "DefaultTaskService{" +
                "taskId='" + taskId + '\'' +
                ", submitterId='" + submitterId + '\'' +
                ", requestId='" + requestId + '\'' +
                ", id='" + id + '\'' +
                ", @" + System.identityHashCode(this) +
                '}';
    }
}
