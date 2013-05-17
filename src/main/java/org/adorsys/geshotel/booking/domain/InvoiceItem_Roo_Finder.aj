// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.booking.domain;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.adorsys.geshotel.booking.domain.Invoice;
import org.adorsys.geshotel.booking.domain.InvoiceItem;

privileged aspect InvoiceItem_Roo_Finder {
    
    public static TypedQuery<InvoiceItem> InvoiceItem.findInvoiceItemsByInvoice(Invoice invoice) {
        if (invoice == null) throw new IllegalArgumentException("The invoice argument is required");
        EntityManager em = InvoiceItem.entityManager();
        TypedQuery<InvoiceItem> q = em.createQuery("SELECT o FROM InvoiceItem AS o WHERE o.invoice = :invoice", InvoiceItem.class);
        q.setParameter("invoice", invoice);
        return q;
    }
    
}