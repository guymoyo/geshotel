// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.booking.domain;

import java.lang.String;
import java.math.BigDecimal;
import java.util.Date;
import org.adorsys.geshotel.domain.Customer;
import org.adorsys.geshotel.domain.UserAccount;

privileged aspect Deposit_Roo_JavaBean {
    
    public String Deposit.getLabel() {
        return this.label;
    }
    
    public void Deposit.setLabel(String label) {
        this.label = label;
    }
    
    public BigDecimal Deposit.getDepositvalue() {
        return this.depositvalue;
    }
    
    public void Deposit.setDepositvalue(BigDecimal depositvalue) {
        this.depositvalue = depositvalue;
    }
    
    public Date Deposit.getDateDeposit() {
        return this.dateDeposit;
    }
    
    public void Deposit.setDateDeposit(Date dateDeposit) {
        this.dateDeposit = dateDeposit;
    }
    
    public UserAccount Deposit.getUserAccount() {
        return this.userAccount;
    }
    
    public void Deposit.setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
    
    public Customer Deposit.getCustomer() {
        return this.customer;
    }
    
    public void Deposit.setCustomer(Customer customer) {
        this.customer = customer;
    }
    
}
