package org.adorsys.geshotel.resto.work.exception;

public class InvalidDateIntervalException extends Exception {

	private String message;

	public InvalidDateIntervalException(String msg) {
		this.message = msg;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
}
