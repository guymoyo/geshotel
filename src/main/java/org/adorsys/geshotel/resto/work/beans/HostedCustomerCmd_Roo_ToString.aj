// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.resto.work.beans;

import java.lang.String;

privileged aspect HostedCustomerCmd_Roo_ToString {
    
    public String HostedCustomerCmd.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ActiveCustomers: ").append(getActiveCustomers() == null ? "null" : getActiveCustomers().size()).append(", ");
        sb.append("Customer: ").append(getCustomer()).append(", ");
        sb.append("OccupiedRooms: ").append(getOccupiedRooms() == null ? "null" : getOccupiedRooms().size()).append(", ");
        sb.append("Room: ").append(getRoom()).append(", ");
        sb.append("SaleKey: ").append(getSaleKey());
        return sb.toString();
    }
    
}
