package org.adorsys.geshotel.resto.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.persistence.EntityManager;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.springframework.format.annotation.DateTimeFormat;
import org.adorsys.geshotel.resto.domain.PeriodConf;
import org.adorsys.geshotel.resto.work.beans.InventoryDateComparator;
import javax.persistence.ManyToOne;
import org.adorsys.geshotel.domain.UserAccount;
import org.adorsys.geshotel.domain.security.SecurityUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.adorsys.geshotel.resto.domain.BarRestauState;
import javax.persistence.Enumerated;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findInventorysByInventoryDateBetween", "findInventorysByIsClosedNot", "findInventorysByPeriodConf", "findInventorysByTitleEquals", "findInventorysByInventoryDateBetweenAndIsClosedNot", "findInventorysByUserAccountAndInventoryState" })
public class Inventory {

    private String title;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private Date inventoryDate;

    @NotNull
    private Boolean isClosed;

    @ManyToOne
    private PeriodConf periodConf;

    @ManyToOne
    private UserAccount userAccount;

    @Enumerated
    private BarRestauState inventoryState;

    public static HashMap<InventoryItem, InventoryItem> findUnMatchingIventoryItems(Inventory arg0, Inventory arg1) throws Exception {
        List<InventoryItem> firstResults = InventoryItem.findInventoryItemsByInventory(arg0).getResultList();
        List<InventoryItem> secondResultList = InventoryItem.findInventoryItemsByInventory(arg1).getResultList();
        HashMap<InventoryItem, InventoryItem> unMatching = new HashMap<InventoryItem, InventoryItem>();
        if (secondResultList.isEmpty()) return unMatching;
        for (InventoryItem inventoryItem : firstResults) {
            if (StringUtils.isBlank(inventoryItem.getDesignation())) continue;
            for (InventoryItem invItem : secondResultList) {
                if (inventoryItem.getDesignation().equals(invItem.getDesignation())) {
                    int compareTo = 0;
                    try {
                        compareTo = inventoryItem.getFinalStock().compareTo(invItem.getInitialStock());
                    } catch (Exception e) {
                        e.printStackTrace();
                        compareTo = 2;
                    }
                    if (compareTo != 0) unMatching.put(inventoryItem, invItem);
                }
            }
        }
        return unMatching;
    }

    public static boolean testInventories(Inventory arg0, Inventory arg1) {
        HashMap<InventoryItem, InventoryItem> unMatchingIventoryItems = new HashMap<InventoryItem, InventoryItem>();
        try {
            unMatchingIventoryItems = findUnMatchingIventoryItems(arg0, arg1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!unMatchingIventoryItems.isEmpty()) return false;
        return true;
    }

    public boolean checkIsCorrectInventory() {
        List<Inventory> inventories = Inventory.findAllInventorys();
        if (inventories.size() <= 1) return true;
        Collections.sort(inventories, new InventoryDateComparator());
        Inventory inv = inventories.get(inventories.size() - 1);
        return testInventories(this, inv);
    }
    public static Inventory findCurrentInventory() {
    	List<Inventory> inventories = findInventorysByUserAccountAndInventoryState(SecurityUtil.getBaseUser(), BarRestauState.OPENED).getResultList();
    	if(inventories== null || inventories.isEmpty()) throw new NullPointerException("No CurrentInventory Found");
    	return inventories.iterator().next();
	}
    public static List<Inventory> findLatestInventory(){
    	EntityManager em = Inventory.entityManager();
    	TypedQuery<Inventory> typedQuery = em.createQuery("SELECT o FROM Inventory AS o ORDER BY o.inventoryDate ASC", Inventory.class);
    	return typedQuery.getResultList();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Inventory other = (Inventory) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
    
}
