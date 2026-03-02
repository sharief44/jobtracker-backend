package com.sharief.jobtracker.exception;

import java.time.LocalDateTime;

public class ErrorResponse {
	
	private LocalDateTime timestamp;
	private int status;
	private String error;
	private String message;
	private String path;
	
	
	public ErrorResponse(int Status,String error, String message,String path) {
		this.status=status;
		this.error=error;
		this.message=message;
		this.path=path;
		this.timestamp=LocalDateTime.now();
	}
public LocalDateTime getTimeStamp() {
	return timestamp;
}
public int getStatus() {
	return status;
}
	
public String getError() {
	return error;
}
public String getPath() {
	return path;
}
public String getMessage() { 
	return message;
}
}
