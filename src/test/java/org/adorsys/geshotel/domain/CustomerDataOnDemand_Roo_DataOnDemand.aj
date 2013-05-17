// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.domain;

import java.lang.Boolean;
import java.lang.String;
import java.math.BigDecimal;
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
import org.adorsys.geshotel.domain.Customer;
import org.adorsys.geshotel.domain.Gender;
import org.springframework.stereotype.Component;

privileged aspect CustomerDataOnDemand_Roo_DataOnDemand {
    
    declare @type: CustomerDataOnDemand: @Component;
    
    private Random CustomerDataOnDemand.rnd = new SecureRandom();
    
    private List<Customer> CustomerDataOnDemand.data;
    
    public Customer CustomerDataOnDemand.getNewTransientCustomer(int index) {
        Customer obj = new Customer();
        setAccount(obj, index);
        setAddress(obj, index);
        setBlackList(obj, index);
        setBornDate(obj, index);
        setBornPlace(obj, index);
        setCarNumber(obj, index);
        setComeFrom(obj, index);
        setDelivredDate(obj, index);
        setEmail(obj, index);
        setFirstName(obj, index);
        setFullName(obj, index);
        setGender(obj, index);
        setGoTo(obj, index);
        setIdentityNumber(obj, index);
        setLastName(obj, index);
        setMaidenName(obj, index);
        setNationality(obj, index);
        setPhoneNumber(obj, index);
        setProfession(obj, index);
        setResidenceCountry(obj, index);
        setTransportMode(obj, index);
        return obj;
    }
    
    public void CustomerDataOnDemand.setAccount(Customer obj, int index) {
        BigDecimal account = BigDecimal.valueOf(index);
        obj.setAccount(account);
    }
    
    public void CustomerDataOnDemand.setAddress(Customer obj, int index) {
        String address = "address_" + index;
        obj.setAddress(address);
    }
    
    public void CustomerDataOnDemand.setBlackList(Customer obj, int index) {
        Boolean blackList = Boolean.TRUE;
        obj.setBlackList(blackList);
    }
    
    public void CustomerDataOnDemand.setBornDate(Customer obj, int index) {
        Date bornDate = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setBornDate(bornDate);
    }
    
    public void CustomerDataOnDemand.setBornPlace(Customer obj, int index) {
        String bornPlace = "bornPlace_" + index;
        obj.setBornPlace(bornPlace);
    }
    
    public void CustomerDataOnDemand.setCarNumber(Customer obj, int index) {
        String carNumber = "carNumber_" + index;
        obj.setCarNumber(carNumber);
    }
    
    public void CustomerDataOnDemand.setComeFrom(Customer obj, int index) {
        String comeFrom = "comeFrom_" + index;
        obj.setComeFrom(comeFrom);
    }
    
    public void CustomerDataOnDemand.setDelivredDate(Customer obj, int index) {
        Date delivredDate = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setDelivredDate(delivredDate);
    }
    
    public void CustomerDataOnDemand.setEmail(Customer obj, int index) {
        String email = "email_" + index;
        obj.setEmail(email);
    }
    
    public void CustomerDataOnDemand.setFirstName(Customer obj, int index) {
        String firstName = "firstName_" + index;
        obj.setFirstName(firstName);
    }
    
    public void CustomerDataOnDemand.setFullName(Customer obj, int index) {
        String fullName = "fullName_" + index;
        obj.setFullName(fullName);
    }
    
    public void CustomerDataOnDemand.setGender(Customer obj, int index) {
        Gender gender = Gender.class.getEnumConstants()[0];
        obj.setGender(gender);
    }
    
    public void CustomerDataOnDemand.setGoTo(Customer obj, int index) {
        String goTo = "goTo_" + index;
        obj.setGoTo(goTo);
    }
    
    public void CustomerDataOnDemand.setIdentityNumber(Customer obj, int index) {
        String identityNumber = "identityNumber_" + index;
        obj.setIdentityNumber(identityNumber);
    }
    
    public void CustomerDataOnDemand.setLastName(Customer obj, int index) {
        String lastName = "lastName_" + index;
        obj.setLastName(lastName);
    }
    
    public void CustomerDataOnDemand.setMaidenName(Customer obj, int index) {
        String maidenName = "maidenName_" + index;
        obj.setMaidenName(maidenName);
    }
    
    public void CustomerDataOnDemand.setNationality(Customer obj, int index) {
        String nationality = "nationality_" + index;
        obj.setNationality(nationality);
    }
    
    public void CustomerDataOnDemand.setPhoneNumber(Customer obj, int index) {
        String phoneNumber = "phoneNumber_" + index;
        obj.setPhoneNumber(phoneNumber);
    }
    
    public void CustomerDataOnDemand.setProfession(Customer obj, int index) {
        String profession = "profession_" + index;
        obj.setProfession(profession);
    }
    
    public void CustomerDataOnDemand.setResidenceCountry(Customer obj, int index) {
        String residenceCountry = "residenceCountry_" + index;
        obj.setResidenceCountry(residenceCountry);
    }
    
    public void CustomerDataOnDemand.setTransportMode(Customer obj, int index) {
        String transportMode = "transportMode_" + index;
        obj.setTransportMode(transportMode);
    }
    
    public Customer CustomerDataOnDemand.getSpecificCustomer(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        Customer obj = data.get(index);
        return Customer.findCustomer(obj.getId());
    }
    
    public Customer CustomerDataOnDemand.getRandomCustomer() {
        init();
        Customer obj = data.get(rnd.nextInt(data.size()));
        return Customer.findCustomer(obj.getId());
    }
    
    public boolean CustomerDataOnDemand.modifyCustomer(Customer obj) {
        return false;
    }
    
    public void CustomerDataOnDemand.init() {
        data = Customer.findCustomerEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Customer' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<org.adorsys.geshotel.domain.Customer>();
        for (int i = 0; i < 10; i++) {
            Customer obj = getNewTransientCustomer(i);
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