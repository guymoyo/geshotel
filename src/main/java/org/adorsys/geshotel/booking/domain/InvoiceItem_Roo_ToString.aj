// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.booking.domain;

import java.lang.String;

privileged aspect InvoiceItem_Roo_ToString {
    
    public String InvoiceItem.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Amount: ").append(getAmount()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Invoice: ").append(getInvoice()).append(", ");
        sb.append("ItemDesc: ").append(getItemDesc()).append(", ");
        sb.append("ItemName: ").append(getItemName()).append(", ");
        sb.append("ItemNumber: ").append(getItemNumber()).append(", ");
        sb.append("Qty: ").append(getQty()).append(", ");
        sb.append("UnitPrice: ").append(getUnitPrice()).append(", ");
        sb.append("Version: ").append(getVersion());
        return sb.toString();
    }
    
}
