package com.sharief.jobtracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex,HttpServletRequest request){
		
		ErrorResponse error  = new ErrorResponse(
				HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_GATEWAY.getReasonPhrase(),
				ex.getMessage(),
				request.getRequestURI()
				);
		
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
				
	}
	 @ExceptionHandler(Exception.class)
	    public ResponseEntity<ErrorResponse> handleGeneralException(
	            Exception ex,
	            HttpServletRequest request) {

	        ErrorResponse error = new ErrorResponse(
	                HttpStatus.INTERNAL_SERVER_ERROR.value(),
	                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
	                "Something went wrong",
	                request.getRequestURI()
	        );

	        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
}
