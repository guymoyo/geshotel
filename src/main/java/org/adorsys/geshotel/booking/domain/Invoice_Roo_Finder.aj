// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.booking.domain;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.adorsys.geshotel.booking.domain.Invoice;
import org.adorsys.geshotel.booking.domain.InvoiceState;
import org.adorsys.geshotel.booking.domain.PayementState;
import org.adorsys.geshotel.domain.Customer;

privileged aspect Invoice_Roo_Finder {
    
    public static TypedQuery<Invoice> Invoice.findInvoicesByCustomer(Customer customer) {
        if (customer == null) throw new IllegalArgumentException("The customer argument is required");
        EntityManager em = Invoice.entityManager();
        TypedQuery<Invoice> q = em.createQuery("SELECT o FROM Invoice AS o WHERE o.customer = :customer", Invoice.class);
        q.setParameter("customer", customer);
        return q;
    }
    
    public static TypedQuery<Invoice> Invoice.findInvoicesByCustomerAndInvoiceState(Customer customer, InvoiceState invoiceState) {
        if (customer == null) throw new IllegalArgumentException("The customer argument is required");
        if (invoiceState == null) throw new IllegalArgumentException("The invoiceState argument is required");
        EntityManager em = Invoice.entityManager();
        TypedQuery<Invoice> q = em.createQuery("SELECT o FROM Invoice AS o WHERE o.customer = :customer AND o.invoiceState = :invoiceState", Invoice.class);
        q.setParameter("customer", customer);
        q.setParameter("invoiceState", invoiceState);
        return q;
    }
    
    public static TypedQuery<Invoice> Invoice.findInvoicesByInvoiceState(InvoiceState invoiceState) {
        if (invoiceState == null) throw new IllegalArgumentException("The invoiceState argument is required");
        EntityManager em = Invoice.entityManager();
        TypedQuery<Invoice> q = em.createQuery("SELECT o FROM Invoice AS o WHERE o.invoiceState = :invoiceState", Invoice.class);
        q.setParameter("invoiceState", invoiceState);
        return q;
    }
    
    public static TypedQuery<Invoice> Invoice.findInvoicesByPayementState(PayementState payementState) {
        if (payementState == null) throw new IllegalArgumentException("The payementState argument is required");
        EntityManager em = Invoice.entityManager();
        TypedQuery<Invoice> q = em.createQuery("SELECT o FROM Invoice AS o WHERE o.payementState = :payementState", Invoice.class);
        q.setParameter("payementState", payementState);
        return q;
    }
    
}
