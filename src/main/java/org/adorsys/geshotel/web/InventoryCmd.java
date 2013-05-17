package org.adorsys.geshotel.web;

import java.util.ArrayList;
import java.util.List;

import org.adorsys.geshotel.resto.domain.Inventory;
import org.adorsys.geshotel.resto.domain.InventoryItem;
import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.ListUtils;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class InventoryCmd {
	private Inventory inventory;
	private List<InventoryItem> inventoryItems = ListUtils.lazyList(new ArrayList<InventoryItem>(), FactoryUtils.instantiateFactory(InventoryItem.class));
	private List<Inventory> almostFilledIventories = new ArrayList<Inventory>();
}
