package org.adorsys.geshotel.resto.work.beans;

import java.util.List;

import org.adorsys.geshotel.resto.domain.KitchenOrder;
import org.adorsys.geshotel.resto.domain.KitchenOrderItem;
import org.adorsys.geshotel.resto.domain.Sale;
import org.adorsys.geshotel.resto.domain.SaleItem;
import org.springframework.roo.addon.json.RooJson;

import flexjson.JSONSerializer;

@RooJson
public class DetailedSaleBean {
	private Sale sale;
	private List<SaleItem> saleItems;
	private KitchenOrder kitchenOrder;
	private List<KitchenOrderItem> kitchenOrderItems;

	public DetailedSaleBean(Sale sale, List<SaleItem> saleItems) {
		super();
		this.sale = sale;
		this.saleItems = saleItems;
	}

	public DetailedSaleBean() {
		super();
	}

	public String toJson() {
		JSONSerializer jsonSerializer = new JSONSerializer();
		jsonSerializer = jsonSerializer.exclude("*.class");
		String serialize = jsonSerializer.deepSerialize(this);
		return serialize;
	}

	public String getSaleItemsToJSon() {
		return new JSONSerializer().exclude("*.class").serialize(saleItems);
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public List<SaleItem> getSaleItems() {
		return saleItems;
	}

	public void setSaleItems(List<SaleItem> saleItems) {
		this.saleItems = saleItems;
	}

	public KitchenOrder getKitchenOrder() {
		return kitchenOrder;
	}

	public void setKitchenOrder(KitchenOrder kitchenOrder) {
		this.kitchenOrder = kitchenOrder;
	}

	public List<KitchenOrderItem> getKitchenOrderItems() {
		return kitchenOrderItems;
	}

	public void setKitchenOrderItems(List<KitchenOrderItem> kitchenOrderItems) {
		this.kitchenOrderItems = kitchenOrderItems;
	}

}
