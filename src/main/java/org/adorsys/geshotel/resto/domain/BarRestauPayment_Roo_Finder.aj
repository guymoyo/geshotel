// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.resto.domain;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.adorsys.geshotel.resto.domain.BarRestauPayment;
import org.adorsys.geshotel.resto.domain.CashRegister;

privileged aspect BarRestauPayment_Roo_Finder {
    
    public static TypedQuery<BarRestauPayment> BarRestauPayment.findBarRestauPaymentsByCashRegister(CashRegister cashRegister) {
        if (cashRegister == null) throw new IllegalArgumentException("The cashRegister argument is required");
        EntityManager em = BarRestauPayment.entityManager();
        TypedQuery<BarRestauPayment> q = em.createQuery("SELECT o FROM BarRestauPayment AS o WHERE o.cashRegister = :cashRegister", BarRestauPayment.class);
        q.setParameter("cashRegister", cashRegister);
        return q;
    }
    
}
