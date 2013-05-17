// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.domain;

import java.lang.String;

privileged aspect UserAccount_Roo_ToString {
    
    public String UserAccount.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AccountExpiration: ").append(getAccountExpiration()).append(", ");
        sb.append("CredentialExpiration: ").append(getCredentialExpiration()).append(", ");
        sb.append("EntryDate: ").append(getEntryDate()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Identifier: ").append(getIdentifier()).append(", ");
        sb.append("LastName: ").append(getLastName()).append(", ");
        sb.append("Password: ").append(getPassword()).append(", ");
        sb.append("PeriodConf: ").append(getPeriodConf()).append(", ");
        sb.append("RoleNames: ").append(getRoleNames() == null ? "null" : getRoleNames().size()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("AccountLocked: ").append(isAccountLocked()).append(", ");
        sb.append("DisableLogin: ").append(isDisableLogin());
        return sb.toString();
    }
    
}
