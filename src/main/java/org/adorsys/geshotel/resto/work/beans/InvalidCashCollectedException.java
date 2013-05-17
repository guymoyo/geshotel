package org.adorsys.geshotel.resto.work.beans;

import java.math.BigDecimal;

import org.adorsys.geshotel.resto.domain.CashRegister;

public class InvalidCashCollectedException extends Exception {

	private CashRegister cashRegister;
	private BigDecimal cashCollected;
	private String message = "Invalid Cash Collected ";
	public InvalidCashCollectedException(BigDecimal cashCollected,
			CashRegister cashRegister) {
		this.cashCollected = cashCollected;
		this.cashRegister = cashRegister;
	}
	public CashRegister getCashRegister() {
		return cashRegister;
	}
	public void setCashRegister(CashRegister cashRegister) {
		this.cashRegister = cashRegister;
	}
	public BigDecimal getCashCollected() {
		return cashCollected;
	}
	public void setCashCollected(BigDecimal cashCollected) {
		this.cashCollected = cashCollected;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
