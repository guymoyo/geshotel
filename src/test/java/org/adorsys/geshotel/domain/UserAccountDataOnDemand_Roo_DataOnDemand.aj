// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.domain;

import java.lang.String;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.adorsys.geshotel.domain.UserAccount;
import org.adorsys.geshotel.resto.domain.PeriodConf;
import org.springframework.stereotype.Component;

privileged aspect UserAccountDataOnDemand_Roo_DataOnDemand {
    
    declare @type: UserAccountDataOnDemand: @Component;
    
    private Random UserAccountDataOnDemand.rnd = new SecureRandom();
    
    private List<UserAccount> UserAccountDataOnDemand.data;
    
    public UserAccount UserAccountDataOnDemand.getNewTransientUserAccount(int index) {
        UserAccount obj = new UserAccount();
        setAccountExpiration(obj, index);
        setAccountLocked(obj, index);
        setCredentialExpiration(obj, index);
        setDisableLogin(obj, index);
        setEntryDate(obj, index);
        setIdentifier(obj, index);
        setLastName(obj, index);
        setPassword(obj, index);
        setPeriodConf(obj, index);
        return obj;
    }
    
    public void UserAccountDataOnDemand.setAccountExpiration(UserAccount obj, int index) {
        Date accountExpiration = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setAccountExpiration(accountExpiration);
    }
    
    public void UserAccountDataOnDemand.setAccountLocked(UserAccount obj, int index) {
        Boolean accountLocked = true;
        obj.setAccountLocked(accountLocked);
    }
    
    public void UserAccountDataOnDemand.setCredentialExpiration(UserAccount obj, int index) {
        Date credentialExpiration = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setCredentialExpiration(credentialExpiration);
    }
    
    public void UserAccountDataOnDemand.setDisableLogin(UserAccount obj, int index) {
        Boolean disableLogin = true;
        obj.setDisableLogin(disableLogin);
    }
    
    public void UserAccountDataOnDemand.setEntryDate(UserAccount obj, int index) {
        Date entryDate = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setEntryDate(entryDate);
    }
    
    public void UserAccountDataOnDemand.setIdentifier(UserAccount obj, int index) {
        String identifier = "identifier_" + index;
        obj.setIdentifier(identifier);
    }
    
    public void UserAccountDataOnDemand.setLastName(UserAccount obj, int index) {
        String lastName = "lastName_" + index;
        obj.setLastName(lastName);
    }
    
    public void UserAccountDataOnDemand.setPassword(UserAccount obj, int index) {
        String password = "password_" + index;
        obj.setPassword(password);
    }
    
    public void UserAccountDataOnDemand.setPeriodConf(UserAccount obj, int index) {
        PeriodConf periodConf = null;
        obj.setPeriodConf(periodConf);
    }
    
    public UserAccount UserAccountDataOnDemand.getSpecificUserAccount(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        UserAccount obj = data.get(index);
        return UserAccount.findUserAccount(obj.getId());
    }
    
    public UserAccount UserAccountDataOnDemand.getRandomUserAccount() {
        init();
        UserAccount obj = data.get(rnd.nextInt(data.size()));
        return UserAccount.findUserAccount(obj.getId());
    }
    
    public boolean UserAccountDataOnDemand.modifyUserAccount(UserAccount obj) {
        return false;
    }
    
    public void UserAccountDataOnDemand.init() {
        data = UserAccount.findUserAccountEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'UserAccount' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<org.adorsys.geshotel.domain.UserAccount>();
        for (int i = 0; i < 10; i++) {
            UserAccount obj = getNewTransientUserAccount(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> it = e.getConstraintViolations().iterator(); it.hasNext();) {
                    ConstraintViolation<?> cv = it.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
    
}
