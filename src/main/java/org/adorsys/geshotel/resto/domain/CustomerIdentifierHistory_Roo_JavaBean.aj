// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.resto.domain;

import java.lang.String;
import java.util.Date;
import org.adorsys.geshotel.domain.Customer;

privileged aspect CustomerIdentifierHistory_Roo_JavaBean {
    
    public String CustomerIdentifierHistory.getHistoryKey() {
        return this.historyKey;
    }
    
    public void CustomerIdentifierHistory.setHistoryKey(String historyKey) {
        this.historyKey = historyKey;
    }
    
    public Customer CustomerIdentifierHistory.getCustomer() {
        return this.customer;
    }
    
    public void CustomerIdentifierHistory.setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public String CustomerIdentifierHistory.getIdentifierValue() {
        return this.identifierValue;
    }
    
    public void CustomerIdentifierHistory.setIdentifierValue(String identifierValue) {
        this.identifierValue = identifierValue;
    }
    
    public Date CustomerIdentifierHistory.getStoringDate() {
        return this.storingDate;
    }
    
    public void CustomerIdentifierHistory.setStoringDate(Date storingDate) {
        this.storingDate = storingDate;
    }
    
}