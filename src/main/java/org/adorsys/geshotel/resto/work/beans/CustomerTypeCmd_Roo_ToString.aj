// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.resto.work.beans;

import java.lang.String;

privileged aspect CustomerTypeCmd_Roo_ToString {
    
    public String CustomerTypeCmd.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ActiveRoom: ").append(getActiveRoom()).append(", ");
        sb.append("CustomerType: ").append(getCustomerType()).append(", ");
        sb.append("CustomerTypes: ").append(getCustomerTypes() == null ? "null" : getCustomerTypes().size()).append(", ");
        sb.append("HostedCustomerIdentifier: ").append(getHostedCustomerIdentifier());
        return sb.toString();
    }
    
}