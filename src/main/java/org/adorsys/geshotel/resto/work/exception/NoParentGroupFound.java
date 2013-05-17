package org.adorsys.geshotel.resto.work.exception;

import org.adorsys.geshotel.resto.domain.ProductGroup;

public class NoParentGroupFound extends Exception {

	private String message;
	private ProductGroup productGroup;

	public NoParentGroupFound(String msg, ProductGroup productGroup) {
		this.message = msg;
		this.productGroup =  productGroup;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ProductGroup getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(ProductGroup productGroup) {
		this.productGroup = productGroup;
	}
	
}
