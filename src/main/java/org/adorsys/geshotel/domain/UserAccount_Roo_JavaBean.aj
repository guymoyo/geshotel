// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.domain;

import java.lang.String;
import java.util.Date;
import java.util.Set;
import org.adorsys.geshotel.domain.RoleName;
import org.adorsys.geshotel.resto.domain.PeriodConf;

privileged aspect UserAccount_Roo_JavaBean {
    
    public String UserAccount.getIdentifier() {
        return this.identifier;
    }
    
    public void UserAccount.setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    
    public String UserAccount.getPassword() {
        return this.password;
    }
    
    public void UserAccount.setPassword(String password) {
        this.password = password;
    }
    
    public String UserAccount.getLastName() {
        return this.lastName;
    }
    
    public void UserAccount.setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public Date UserAccount.getEntryDate() {
        return this.entryDate;
    }
    
    public void UserAccount.setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }
    
    public Set<RoleName> UserAccount.getRoleNames() {
        return this.roleNames;
    }
    
    public void UserAccount.setRoleNames(Set<RoleName> roleNames) {
        this.roleNames = roleNames;
    }
    
    public Date UserAccount.getAccountExpiration() {
        return this.accountExpiration;
    }
    
    public void UserAccount.setAccountExpiration(Date accountExpiration) {
        this.accountExpiration = accountExpiration;
    }
    
    public boolean UserAccount.isDisableLogin() {
        return this.disableLogin;
    }
    
    public void UserAccount.setDisableLogin(boolean disableLogin) {
        this.disableLogin = disableLogin;
    }
    
    public boolean UserAccount.isAccountLocked() {
        return this.accountLocked;
    }
    
    public void UserAccount.setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }
    
    public Date UserAccount.getCredentialExpiration() {
        return this.credentialExpiration;
    }
    
    public void UserAccount.setCredentialExpiration(Date credentialExpiration) {
        this.credentialExpiration = credentialExpiration;
    }
    
    public PeriodConf UserAccount.getPeriodConf() {
        return this.periodConf;
    }
    
    public void UserAccount.setPeriodConf(PeriodConf periodConf) {
        this.periodConf = periodConf;
    }
    
}
