// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.resto.domain;

import java.lang.String;

privileged aspect BillOfProcurement_Roo_ToString {
    
    public String BillOfProcurement.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AmountDue: ").append(getAmountDue()).append(", ");
        sb.append("Broker: ").append(getBroker()).append(", ");
        sb.append("DateOfProcurement: ").append(getDateOfProcurement()).append(", ");
        sb.append("Discount: ").append(getDiscount()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Note: ").append(getNote()).append(", ");
        sb.append("RestaurantName: ").append(getRestaurantName()).append(", ");
        sb.append("SaleItems: ").append(getSaleItems() == null ? "null" : getSaleItems().size()).append(", ");
        sb.append("SellKey: ").append(getSellKey()).append(", ");
        sb.append("Tax: ").append(getTax()).append(", ");
        sb.append("TotalAmount: ").append(getTotalAmount()).append(", ");
        sb.append("Version: ").append(getVersion());
        return sb.toString();
    }
    
}
