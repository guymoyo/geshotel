package org.adorsys.geshotel.resto.work.exception;

public class SuspendSupendeCashRegisterException extends Exception {

	private String message;

	public SuspendSupendeCashRegisterException(String msg) {
		this.message = msg;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
