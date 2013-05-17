package org.adorsys.geshotel.resto.work.exception;

public class SuspendClosedCashRegisterException extends Exception {

	private String message;

	public SuspendClosedCashRegisterException(String msg) {
		this.message = msg;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
