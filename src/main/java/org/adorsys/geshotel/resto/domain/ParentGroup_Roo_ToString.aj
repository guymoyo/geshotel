// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.resto.domain;

import java.lang.String;

privileged aspect ParentGroup_Roo_ToString {
    
    public String ParentGroup.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Description: ").append(getDescription()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("ParentGroupKey: ").append(getParentGroupKey()).append(", ");
        sb.append("ProductGroupKey: ").append(getProductGroupKey()).append(", ");
        sb.append("ProductGroupParentKey: ").append(getProductGroupParentKey()).append(", ");
        sb.append("Version: ").append(getVersion());
        return sb.toString();
    }
    
}