package org.adorsys.geshotel.resto.work.beans;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.adorsys.geshotel.resto.domain.Inventory;
import org.adorsys.geshotel.resto.domain.InventoryItem;
import org.apache.commons.lang3.StringUtils;

public class InventoryByItemComparator implements Comparator<Inventory> {

	@Override
	public int compare(Inventory arg0, Inventory arg1) {
		List<InventoryItem> firstResults = InventoryItem.findInventoryItemsByInventory(arg0).getResultList();
		List<InventoryItem> secondResultList = InventoryItem.findInventoryItemsByInventory(arg1).getResultList();
		HashMap<InventoryItem, InventoryItem> unMatching = new HashMap<InventoryItem, InventoryItem>();
		for (InventoryItem inventoryItem : firstResults) {
			if(StringUtils.isBlank(inventoryItem.getDesignation())) continue;
			for (InventoryItem invItem : secondResultList) {
				if(inventoryItem.getDesignation().equals(invItem.getDesignation())){
					if(!(inventoryItem.getFinalStock().doubleValue() == invItem.getInitialStock().doubleValue())) unMatching.put(inventoryItem, invItem);
				}
			}
		}
		return 0;
	}

}
