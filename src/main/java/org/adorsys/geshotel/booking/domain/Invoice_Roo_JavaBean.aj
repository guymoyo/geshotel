// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.booking.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.adorsys.geshotel.booking.domain.InvoiceState;
import org.adorsys.geshotel.booking.domain.PayementState;
import org.adorsys.geshotel.domain.Customer;
import org.adorsys.geshotel.domain.Payment;
import org.adorsys.geshotel.domain.UserAccount;

privileged aspect Invoice_Roo_JavaBean {
    
    public Customer Invoice.getCustomer() {
        return this.customer;
    }
    
    public void Invoice.setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public BigDecimal Invoice.getInvoiceAmount() {
        return this.invoiceAmount;
    }
    
    public void Invoice.setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }
    
    public BigDecimal Invoice.getValueAdddedTax() {
        return this.valueAdddedTax;
    }
    
    public void Invoice.setValueAdddedTax(BigDecimal valueAdddedTax) {
        this.valueAdddedTax = valueAdddedTax;
    }
    
    public BigDecimal Invoice.getOtherTaxes() {
        return this.otherTaxes;
    }
    
    public void Invoice.setOtherTaxes(BigDecimal otherTaxes) {
        this.otherTaxes = otherTaxes;
    }
    
    public BigDecimal Invoice.getAmountDue() {
        return this.amountDue;
    }
    
    public void Invoice.setAmountDue(BigDecimal amountDue) {
        this.amountDue = amountDue;
    }
    
    public BigDecimal Invoice.getAmountPaid() {
        return this.amountPaid;
    }
    
    public void Invoice.setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }
    
    public BigDecimal Invoice.getReste() {
        return this.reste;
    }
    
    public void Invoice.setReste(BigDecimal reste) {
        this.reste = reste;
    }
    
    public BigDecimal Invoice.getMontantService() {
        return this.montantService;
    }
    
    public void Invoice.setMontantService(BigDecimal montantService) {
        this.montantService = montantService;
    }
    
    public List<Payment> Invoice.getPaiements() {
        return this.paiements;
    }
    
    public void Invoice.setPaiements(List<Payment> paiements) {
        this.paiements = paiements;
    }
    
    public PayementState Invoice.getPayementState() {
        return this.payementState;
    }
    
    public void Invoice.setPayementState(PayementState payementState) {
        this.payementState = payementState;
    }
    
    public InvoiceState Invoice.getInvoiceState() {
        return this.invoiceState;
    }
    
    public void Invoice.setInvoiceState(InvoiceState invoiceState) {
        this.invoiceState = invoiceState;
    }
    
    public UserAccount Invoice.getOriginator() {
        return this.originator;
    }
    
    public void Invoice.setOriginator(UserAccount originator) {
        this.originator = originator;
    }
    
    public Date Invoice.getDateOfClose() {
        return this.dateOfClose;
    }
    
    public void Invoice.setDateOfClose(Date dateOfClose) {
        this.dateOfClose = dateOfClose;
    }
    
    public Date Invoice.getDateOfCreate() {
        return this.dateOfCreate;
    }
    
    public void Invoice.setDateOfCreate(Date dateOfCreate) {
        this.dateOfCreate = dateOfCreate;
    }
    
}
