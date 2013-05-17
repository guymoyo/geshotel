package org.adorsys.geshotel.resto.work.exception;

import java.math.BigDecimal;

public class MoreProductSaledThanInStockException extends Exception {
	
	private BigDecimal qtyInStock ;
	private BigDecimal qtySaled;
	private BigDecimal qtyToSale ;
	
	private String message ;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MoreProductSaledThanInStockException(String message,BigDecimal qtyInStock,BigDecimal qtySaled, BigDecimal qtyToSale) {
		this.message = message;
		this.qtyInStock = qtyInStock;
		this.qtySaled = qtySaled;
		this.qtyToSale = qtyToSale;
	}
	public MoreProductSaledThanInStockException(BigDecimal qtyInStock,BigDecimal qtySaled, BigDecimal qtyToSale) {
		this("you can't sale more than quantity in stock. ",qtyInStock, qtySaled,qtyToSale);
	}
	public BigDecimal getQtyInStock() {
		return qtyInStock;
	}
	public void setQtyInStock(BigDecimal qtyInStock) {
		this.qtyInStock = qtyInStock;
	}
	public BigDecimal getQtySaled() {
		return qtySaled;
	}
	public void setQtySaled(BigDecimal qtySaled) {
		this.qtySaled = qtySaled;
	}
	public BigDecimal getQtyToSale() {
		return qtyToSale;
	}
	public void setQtyToSale(BigDecimal qtyToSale) {
		this.qtyToSale = qtyToSale;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "[message=" + message + ", qty In Stock=" + qtyInStock
				+ ", qty Saled=" + qtySaled + ", qty To Sale=" + qtyToSale+"]";
	}
	
}
