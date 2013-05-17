// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.resto.domain;

import java.lang.String;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.adorsys.geshotel.resto.domain.Sale;
import org.adorsys.geshotel.resto.domain.SaleItem;

privileged aspect SaleItem_Roo_Finder {
    
    public static TypedQuery<SaleItem> SaleItem.findSaleItemsBySale(Sale sale) {
        if (sale == null) throw new IllegalArgumentException("The sale argument is required");
        EntityManager em = SaleItem.entityManager();
        TypedQuery<SaleItem> q = em.createQuery("SELECT o FROM SaleItem AS o WHERE o.sale = :sale", SaleItem.class);
        q.setParameter("sale", sale);
        return q;
    }
    
    public static TypedQuery<SaleItem> SaleItem.findSaleItemsBySaleItemKeyEquals(String saleItemKey) {
        if (saleItemKey == null || saleItemKey.length() == 0) throw new IllegalArgumentException("The saleItemKey argument is required");
        EntityManager em = SaleItem.entityManager();
        TypedQuery<SaleItem> q = em.createQuery("SELECT o FROM SaleItem AS o WHERE o.saleItemKey = :saleItemKey", SaleItem.class);
        q.setParameter("saleItemKey", saleItemKey);
        return q;
    }
    
}
