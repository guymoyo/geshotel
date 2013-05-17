package org.adorsys.geshotel.resto.work.beans;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.adorsys.geshotel.domain.ext.IdGenerator;
import org.adorsys.geshotel.domain.security.SecurityUtil;
import org.adorsys.geshotel.resto.domain.KitchenOrder;
import org.adorsys.geshotel.resto.domain.KitchenOrderItem;
import org.adorsys.geshotel.resto.domain.Product;
import org.adorsys.geshotel.resto.domain.Sale;
import org.adorsys.geshotel.resto.domain.SaleItem;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.util.Assert;
@RooJavaBean
@RooToString
public class SaleItemCommand {
	private String saleKey;
//	private String designation;
	private String productKey;
	@NotNull
	private int qty;
//	@NotNull
	private BigDecimal unitPrice;
	private BigDecimal amount;
	private Sale sale;
	private Product product;
	
	/**
	 * create a new sale item from this command
	 * @return 
	 */
	public SaleItem newSaleItem(){
		SaleItem saleItem = new SaleItem();
		saleItem.setDateOfSale(new Date());
		saleItem.setQty(new BigDecimal(qty));
		product = Product.findProductsByProductKeyEquals(getProductKey()).getResultList().iterator().next();
		unitPrice = product.getPrice();
		saleItem.setUnitPrice(unitPrice);
		saleItem.setAmount(unitPrice.multiply(new BigDecimal(qty)));
		saleItem.setSeller(SecurityUtil.getUserName());
		saleItem.setSaleItemKey(IdGenerator.generateId());
		saleItem.setSale(Sale.findSalesBySaleKeyEquals(getSaleKey()).getResultList().iterator().next());
    	System.out.println("Product :  "+product);
    	saleItem.setProduct(product);
    	System.out.println(" \n Sale Item : "+saleItem.toString());;
		return saleItem;
	}
	public KitchenOrderItem newKitchenItemOrder(KitchenOrder kitchenOrder){
		KitchenOrderItem kitchenOrderItem = new KitchenOrderItem();
		kitchenOrderItem.setDateOfOrdering(new Date());
		kitchenOrderItem.setKitchenOrderItemKey(IdGenerator.generateId());
		kitchenOrderItem.setProduct(Product.findProductsByProductKeyEquals(getProductKey()).getSingleResult());
		kitchenOrderItem.setQty(new BigDecimal(qty));
		Assert.notNull(kitchenOrder, "Null Value is Not required");
		kitchenOrderItem.setKitchenOrder(kitchenOrder);
		return kitchenOrderItem;
	}
	/**
	 * I use this method to retrie the Product of this command.
	 */

    public Product getProduct() {
    	return Product.findProductsByProductKeyEquals(getProductKey()).getSingleResult();
    }

    public Sale getSale() {
    	return Sale.findSalesBySaleKeyEquals(getSaleKey()).getResultList().iterator().next();
    }
}
