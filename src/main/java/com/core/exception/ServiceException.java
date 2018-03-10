package com.core.exception;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String code =  "service.error";
	
	public ServiceException() {
		super();
	}

	public ServiceException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public ServiceException(String code, Throwable cause) {
		super(cause);
		this.code = code;
	}

	public ServiceException(String code, String message) {
		super(message);
		this.code = code;
	}

	public ServiceException(String code) {
		this.code = code;
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public String getCode() {
		return code;
	}
	
}
