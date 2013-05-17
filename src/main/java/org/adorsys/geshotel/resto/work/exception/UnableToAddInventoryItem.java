package org.adorsys.geshotel.resto.work.exception;

import org.adorsys.geshotel.resto.domain.Inventory;

public class UnableToAddInventoryItem extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Inventory inventory;
	private String message ;
	public UnableToAddInventoryItem(String message,Inventory inventory){
		this.inventory = inventory;
		this.message = message;
	}
	public UnableToAddInventoryItem(Inventory inventory){
		this("Unable to add Inventory Item. On this Inventory"+inventory.getTitle(),inventory);
	}
	public UnableToAddInventoryItem(){
		this(null);
	}
	public Inventory getInventory() {
		return inventory;
	}
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
