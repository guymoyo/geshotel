// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.resto.domain;

import java.lang.String;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.adorsys.geshotel.resto.domain.ParentGroup;

privileged aspect ParentGroup_Roo_Finder {
    
    public static TypedQuery<ParentGroup> ParentGroup.findParentGroupsByParentGroupKeyEquals(String parentGroupKey) {
        if (parentGroupKey == null || parentGroupKey.length() == 0) throw new IllegalArgumentException("The parentGroupKey argument is required");
        EntityManager em = ParentGroup.entityManager();
        TypedQuery<ParentGroup> q = em.createQuery("SELECT o FROM ParentGroup AS o WHERE o.parentGroupKey = :parentGroupKey", ParentGroup.class);
        q.setParameter("parentGroupKey", parentGroupKey);
        return q;
    }
    
    public static TypedQuery<ParentGroup> ParentGroup.findParentGroupsByProductGroupKeyEquals(String productGroupKey) {
        if (productGroupKey == null || productGroupKey.length() == 0) throw new IllegalArgumentException("The productGroupKey argument is required");
        EntityManager em = ParentGroup.entityManager();
        TypedQuery<ParentGroup> q = em.createQuery("SELECT o FROM ParentGroup AS o WHERE o.productGroupKey = :productGroupKey", ParentGroup.class);
        q.setParameter("productGroupKey", productGroupKey);
        return q;
    }
    
    public static TypedQuery<ParentGroup> ParentGroup.findParentGroupsByProductGroupParentKeyEquals(String productGroupParentKey) {
        if (productGroupParentKey == null || productGroupParentKey.length() == 0) throw new IllegalArgumentException("The productGroupParentKey argument is required");
        EntityManager em = ParentGroup.entityManager();
        TypedQuery<ParentGroup> q = em.createQuery("SELECT o FROM ParentGroup AS o WHERE o.productGroupParentKey = :productGroupParentKey", ParentGroup.class);
        q.setParameter("productGroupParentKey", productGroupParentKey);
        return q;
    }
    
}