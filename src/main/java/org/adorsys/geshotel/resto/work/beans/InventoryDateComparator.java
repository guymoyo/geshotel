package org.adorsys.geshotel.resto.work.beans;

import java.util.Comparator;

import org.adorsys.geshotel.resto.domain.Inventory;

public class InventoryDateComparator implements Comparator<Inventory> {

	@Override
	public int compare(Inventory arg0, Inventory arg1) {
		return arg0.getInventoryDate().compareTo(arg1.getInventoryDate());
	}

}
