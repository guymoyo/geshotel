// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.booking.domain;

import java.lang.String;

privileged aspect Deposit_Roo_ToString {
    
    public String Deposit.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Customer: ").append(getCustomer()).append(", ");
        sb.append("DateDeposit: ").append(getDateDeposit()).append(", ");
        sb.append("Depositvalue: ").append(getDepositvalue()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Label: ").append(getLabel()).append(", ");
        sb.append("UserAccount: ").append(getUserAccount()).append(", ");
        sb.append("Version: ").append(getVersion());
        return sb.toString();
    }
    
}