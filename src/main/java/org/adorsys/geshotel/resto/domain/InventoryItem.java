package org.adorsys.geshotel.resto.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

import org.adorsys.geshotel.resto.domain.Inventory;
import javax.persistence.ManyToOne;
import org.adorsys.geshotel.resto.domain.Product;
import org.apache.commons.lang3.StringUtils;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findInventoryItemsByInventory", "findInventoryItemsByDesignationEquals", "findInventoryItemsByInventoryAndDesignationEquals" })
public class InventoryItem {

    @NotNull
    private String designation;

    private BigDecimal entry;

    private BigDecimal sales;

    private BigDecimal initialStock;

    private BigDecimal finalStock;

    @ManyToOne
    private Inventory inventory;

    @ManyToOne
    private Product product;

    public static boolean testIventoryItems(InventoryItem first, InventoryItem second) {
        String firstDesig = first.getDesignation();
        String secondDesig = second.getDesignation();
        BigDecimal firstFinalStock = first.getFinalStock();
        BigDecimal secondInitStock = second.getInitialStock();
        if (StringUtils.isBlank(secondDesig) || StringUtils.isBlank(firstDesig)) return false;
        if (firstFinalStock == null || secondInitStock == null) return false;
        if (!firstFinalStock.equals(secondInitStock)) return false;
        return true;
    }
    public static List<InventoryItem> findCurrentInventoryItems(){
    	Inventory currentInventory = Inventory.findCurrentInventory();
    	return findInventoryItemsByInventory(currentInventory).getResultList();
    }
}
