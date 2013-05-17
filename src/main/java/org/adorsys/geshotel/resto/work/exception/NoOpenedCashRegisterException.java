package org.adorsys.geshotel.resto.work.exception;

public class NoOpenedCashRegisterException extends Exception {

	private String message;

	public NoOpenedCashRegisterException(String msg) {
		this.message = msg;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
