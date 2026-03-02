package com.sharief.jobtracker.exception;

public class JobNotFoundException extends RuntimeException {

	public JobNotFoundException(String message) {
		super(message);
	}
}