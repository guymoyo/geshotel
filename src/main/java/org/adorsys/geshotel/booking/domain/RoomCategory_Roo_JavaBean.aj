// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.booking.domain;

import java.lang.String;
import java.math.BigDecimal;

privileged aspect RoomCategory_Roo_JavaBean {
    
    public String RoomCategory.getTitle() {
        return this.title;
    }
    
    public void RoomCategory.setTitle(String title) {
        this.title = title;
    }
    
    public String RoomCategory.getDescription() {
        return this.description;
    }
    
    public void RoomCategory.setDescription(String description) {
        this.description = description;
    }
    
    public BigDecimal RoomCategory.getPrice() {
        return this.price;
    }
    
    public void RoomCategory.setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public BigDecimal RoomCategory.getPriceMin() {
        return this.priceMin;
    }
    
    public void RoomCategory.setPriceMin(BigDecimal priceMin) {
        this.priceMin = priceMin;
    }
    
}
