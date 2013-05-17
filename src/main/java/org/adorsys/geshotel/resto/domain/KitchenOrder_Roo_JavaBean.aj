// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.resto.domain;

import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import org.adorsys.geshotel.domain.UserAccount;
import org.adorsys.geshotel.resto.domain.BarRestauState;
import org.adorsys.geshotel.resto.domain.Sale;

privileged aspect KitchenOrder_Roo_JavaBean {
    
    public Date KitchenOrder.getDateOfOrdering() {
        return this.dateOfOrdering;
    }
    
    public void KitchenOrder.setDateOfOrdering(Date dateOfOrdering) {
        this.dateOfOrdering = dateOfOrdering;
    }
    
    public Sale KitchenOrder.getSale() {
        return this.sale;
    }
    
    public void KitchenOrder.setSale(Sale sale) {
        this.sale = sale;
    }
    
    public BarRestauState KitchenOrder.getKitchenOrderItemState() {
        return this.kitchenOrderItemState;
    }
    
    public void KitchenOrder.setKitchenOrderItemState(BarRestauState kitchenOrderItemState) {
        this.kitchenOrderItemState = kitchenOrderItemState;
    }
    
    public UserAccount KitchenOrder.getSellerAccount() {
        return this.sellerAccount;
    }
    
    public void KitchenOrder.setSellerAccount(UserAccount sellerAccount) {
        this.sellerAccount = sellerAccount;
    }
    
    public String KitchenOrder.getKitchenOrderPath() {
        return this.kitchenOrderPath;
    }
    
    public void KitchenOrder.setKitchenOrderPath(String kitchenOrderPath) {
        this.kitchenOrderPath = kitchenOrderPath;
    }
    
    public Integer KitchenOrder.getKitchenOrderNumber() {
        return this.kitchenOrderNumber;
    }
    
    public void KitchenOrder.setKitchenOrderNumber(Integer kitchenOrderNumber) {
        this.kitchenOrderNumber = kitchenOrderNumber;
    }
    
    public String KitchenOrder.getKitchenOrderKey() {
        return this.kitchenOrderKey;
    }
    
    public void KitchenOrder.setKitchenOrderKey(String kitchenOrderKey) {
        this.kitchenOrderKey = kitchenOrderKey;
    }
    
}