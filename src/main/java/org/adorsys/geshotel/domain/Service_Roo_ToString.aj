// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.domain;

import java.lang.String;

privileged aspect Service_Roo_ToString {
    
    public String Service.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Periods: ").append(getPeriods() == null ? "null" : getPeriods().size()).append(", ");
        sb.append("Title: ").append(getTitle()).append(", ");
        sb.append("Version: ").append(getVersion());
        return sb.toString();
    }
    
}
