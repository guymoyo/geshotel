// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.booking.domain;

import java.util.Date;

privileged aspect Periode_Roo_JavaBean {
    
    public Date Periode.getMinDate() {
        return this.minDate;
    }
    
    public void Periode.setMinDate(Date minDate) {
        this.minDate = minDate;
    }
    
    public Date Periode.getMaxDate() {
        return this.maxDate;
    }
    
    public void Periode.setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }
    
}
