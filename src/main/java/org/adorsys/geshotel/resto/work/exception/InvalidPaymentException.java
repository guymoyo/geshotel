package org.adorsys.geshotel.resto.work.exception;

import org.adorsys.geshotel.resto.domain.BarRestauInvoice;


public class InvalidPaymentException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message ;
	private BarRestauInvoice barRestauInvoice;
	public InvalidPaymentException(String message) {
		this(message,null);
	}
	public InvalidPaymentException(String message,BarRestauInvoice barRestauInvoice) {
		this.message = message;
		this.barRestauInvoice = barRestauInvoice;
	}
	public InvalidPaymentException(){
		this("Invalid Payment Occurs. Check If the [Amount In] Is not smaller that The [Amount due] !");
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public BarRestauInvoice getBarRestauInvoice() {
		return barRestauInvoice;
	}
	public void setBarRestauInvoice(BarRestauInvoice barRestauInvoice) {
		this.barRestauInvoice = barRestauInvoice;
	}
	
}