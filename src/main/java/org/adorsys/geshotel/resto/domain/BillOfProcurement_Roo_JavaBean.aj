// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.resto.domain;

import java.lang.String;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import org.adorsys.geshotel.resto.domain.SaleItem;

privileged aspect BillOfProcurement_Roo_JavaBean {
    
    public String BillOfProcurement.getSellKey() {
        return this.sellKey;
    }
    
    public void BillOfProcurement.setSellKey(String sellKey) {
        this.sellKey = sellKey;
    }
    
    public Set<SaleItem> BillOfProcurement.getSaleItems() {
        return this.saleItems;
    }
    
    public void BillOfProcurement.setSaleItems(Set<SaleItem> saleItems) {
        this.saleItems = saleItems;
    }
    
    public BigDecimal BillOfProcurement.getTotalAmount() {
        return this.totalAmount;
    }
    
    public void BillOfProcurement.setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public BigDecimal BillOfProcurement.getTax() {
        return this.tax;
    }
    
    public void BillOfProcurement.setTax(BigDecimal tax) {
        this.tax = tax;
    }
    
    public BigDecimal BillOfProcurement.getDiscount() {
        return this.discount;
    }
    
    public void BillOfProcurement.setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
    
    public BigDecimal BillOfProcurement.getAmountDue() {
        return this.amountDue;
    }
    
    public void BillOfProcurement.setAmountDue(BigDecimal amountDue) {
        this.amountDue = amountDue;
    }
    
    public String BillOfProcurement.getNote() {
        return this.note;
    }
    
    public void BillOfProcurement.setNote(String note) {
        this.note = note;
    }
    
    public String BillOfProcurement.getRestaurantName() {
        return this.restaurantName;
    }
    
    public void BillOfProcurement.setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
    
    public String BillOfProcurement.getBroker() {
        return this.broker;
    }
    
    public void BillOfProcurement.setBroker(String broker) {
        this.broker = broker;
    }
    
    public Date BillOfProcurement.getDateOfProcurement() {
        return this.dateOfProcurement;
    }
    
    public void BillOfProcurement.setDateOfProcurement(Date dateOfProcurement) {
        this.dateOfProcurement = dateOfProcurement;
    }
    
}