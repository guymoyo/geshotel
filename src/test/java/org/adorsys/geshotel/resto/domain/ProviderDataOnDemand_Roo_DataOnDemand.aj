// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.resto.domain;

import java.lang.String;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.adorsys.geshotel.resto.domain.Provider;
import org.springframework.stereotype.Component;

privileged aspect ProviderDataOnDemand_Roo_DataOnDemand {
    
    declare @type: ProviderDataOnDemand: @Component;
    
    private Random ProviderDataOnDemand.rnd = new SecureRandom();
    
    private List<Provider> ProviderDataOnDemand.data;
    
    public Provider ProviderDataOnDemand.getNewTransientProvider(int index) {
        Provider obj = new Provider();
        setFullName(obj, index);
        setIdentifier(obj, index);
        setProviderKey(obj, index);
        return obj;
    }
    
    public void ProviderDataOnDemand.setFullName(Provider obj, int index) {
        String fullName = "fullName_" + index;
        obj.setFullName(fullName);
    }
    
    public void ProviderDataOnDemand.setIdentifier(Provider obj, int index) {
        String identifier = "identifier_" + index;
        obj.setIdentifier(identifier);
    }
    
    public void ProviderDataOnDemand.setProviderKey(Provider obj, int index) {
        String providerKey = "providerKey_" + index;
        obj.setProviderKey(providerKey);
    }
    
    public Provider ProviderDataOnDemand.getSpecificProvider(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        Provider obj = data.get(index);
        return Provider.findProvider(obj.getId());
    }
    
    public Provider ProviderDataOnDemand.getRandomProvider() {
        init();
        Provider obj = data.get(rnd.nextInt(data.size()));
        return Provider.findProvider(obj.getId());
    }
    
    public boolean ProviderDataOnDemand.modifyProvider(Provider obj) {
        return false;
    }
    
    public void ProviderDataOnDemand.init() {
        data = Provider.findProviderEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Provider' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<org.adorsys.geshotel.resto.domain.Provider>();
        for (int i = 0; i < 10; i++) {
            Provider obj = getNewTransientProvider(i);
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
