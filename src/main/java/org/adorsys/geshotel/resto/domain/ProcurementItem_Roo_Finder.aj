// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.resto.domain;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.adorsys.geshotel.resto.domain.Procurement;
import org.adorsys.geshotel.resto.domain.ProcurementItem;
import org.adorsys.geshotel.resto.domain.Product;

privileged aspect ProcurementItem_Roo_Finder {
    
    public static TypedQuery<ProcurementItem> ProcurementItem.findProcurementItemsByProcurement(Procurement procurement) {
        if (procurement == null) throw new IllegalArgumentException("The procurement argument is required");
        EntityManager em = ProcurementItem.entityManager();
        TypedQuery<ProcurementItem> q = em.createQuery("SELECT o FROM ProcurementItem AS o WHERE o.procurement = :procurement", ProcurementItem.class);
        q.setParameter("procurement", procurement);
        return q;
    }
    
    public static TypedQuery<ProcurementItem> ProcurementItem.findProcurementItemsByProcurementAndProduct(Procurement procurement, Product product) {
        if (procurement == null) throw new IllegalArgumentException("The procurement argument is required");
        if (product == null) throw new IllegalArgumentException("The product argument is required");
        EntityManager em = ProcurementItem.entityManager();
        TypedQuery<ProcurementItem> q = em.createQuery("SELECT o FROM ProcurementItem AS o WHERE o.procurement = :procurement AND o.product = :product", ProcurementItem.class);
        q.setParameter("procurement", procurement);
        q.setParameter("product", product);
        return q;
    }
    
}