package org.adorsys.geshotel.resto.work.exception;

public class ActivateOpenedCashRegisterException extends Exception {

	private String message;

	public ActivateOpenedCashRegisterException(String msg) {
		this.message = msg;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
