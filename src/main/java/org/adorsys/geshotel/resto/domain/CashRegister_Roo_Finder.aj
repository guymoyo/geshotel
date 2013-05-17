// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.resto.domain;

import java.lang.String;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.adorsys.geshotel.resto.domain.CashRegister;

privileged aspect CashRegister_Roo_Finder {
    
    public static TypedQuery<CashRegister> CashRegister.findCashRegistersByCashRegisterKeyEquals(String cashRegisterKey) {
        if (cashRegisterKey == null || cashRegisterKey.length() == 0) throw new IllegalArgumentException("The cashRegisterKey argument is required");
        EntityManager em = CashRegister.entityManager();
        TypedQuery<CashRegister> q = em.createQuery("SELECT o FROM CashRegister AS o WHERE o.cashRegisterKey = :cashRegisterKey", CashRegister.class);
        q.setParameter("cashRegisterKey", cashRegisterKey);
        return q;
    }
    
}