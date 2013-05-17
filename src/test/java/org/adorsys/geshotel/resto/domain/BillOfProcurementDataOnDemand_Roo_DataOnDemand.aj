// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.resto.domain;

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
import org.adorsys.geshotel.resto.domain.BillOfProcurement;
import org.springframework.stereotype.Component;

privileged aspect BillOfProcurementDataOnDemand_Roo_DataOnDemand {
    
    declare @type: BillOfProcurementDataOnDemand: @Component;
    
    private Random BillOfProcurementDataOnDemand.rnd = new SecureRandom();
    
    private List<BillOfProcurement> BillOfProcurementDataOnDemand.data;
    
    public BillOfProcurement BillOfProcurementDataOnDemand.getNewTransientBillOfProcurement(int index) {
        BillOfProcurement obj = new BillOfProcurement();
        setAmountDue(obj, index);
        setBroker(obj, index);
        setDateOfProcurement(obj, index);
        setDiscount(obj, index);
        setNote(obj, index);
        setRestaurantName(obj, index);
        setSellKey(obj, index);
        setTax(obj, index);
        setTotalAmount(obj, index);
        return obj;
    }
    
    public void BillOfProcurementDataOnDemand.setAmountDue(BillOfProcurement obj, int index) {
        BigDecimal amountDue = BigDecimal.valueOf(index);
        obj.setAmountDue(amountDue);
    }
    
    public void BillOfProcurementDataOnDemand.setBroker(BillOfProcurement obj, int index) {
        String broker = "broker_" + index;
        obj.setBroker(broker);
    }
    
    public void BillOfProcurementDataOnDemand.setDateOfProcurement(BillOfProcurement obj, int index) {
        Date dateOfProcurement = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setDateOfProcurement(dateOfProcurement);
    }
    
    public void BillOfProcurementDataOnDemand.setDiscount(BillOfProcurement obj, int index) {
        BigDecimal discount = BigDecimal.valueOf(index);
        obj.setDiscount(discount);
    }
    
    public void BillOfProcurementDataOnDemand.setNote(BillOfProcurement obj, int index) {
        String note = "note_" + index;
        obj.setNote(note);
    }
    
    public void BillOfProcurementDataOnDemand.setRestaurantName(BillOfProcurement obj, int index) {
        String restaurantName = "restaurantName_" + index;
        obj.setRestaurantName(restaurantName);
    }
    
    public void BillOfProcurementDataOnDemand.setSellKey(BillOfProcurement obj, int index) {
        String sellKey = "sellKey_" + index;
        obj.setSellKey(sellKey);
    }
    
    public void BillOfProcurementDataOnDemand.setTax(BillOfProcurement obj, int index) {
        BigDecimal tax = BigDecimal.valueOf(index);
        obj.setTax(tax);
    }
    
    public void BillOfProcurementDataOnDemand.setTotalAmount(BillOfProcurement obj, int index) {
        BigDecimal totalAmount = BigDecimal.valueOf(index);
        obj.setTotalAmount(totalAmount);
    }
    
    public BillOfProcurement BillOfProcurementDataOnDemand.getSpecificBillOfProcurement(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        BillOfProcurement obj = data.get(index);
        return BillOfProcurement.findBillOfProcurement(obj.getId());
    }
    
    public BillOfProcurement BillOfProcurementDataOnDemand.getRandomBillOfProcurement() {
        init();
        BillOfProcurement obj = data.get(rnd.nextInt(data.size()));
        return BillOfProcurement.findBillOfProcurement(obj.getId());
    }
    
    public boolean BillOfProcurementDataOnDemand.modifyBillOfProcurement(BillOfProcurement obj) {
        return false;
    }
    
    public void BillOfProcurementDataOnDemand.init() {
        data = BillOfProcurement.findBillOfProcurementEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'BillOfProcurement' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<org.adorsys.geshotel.resto.domain.BillOfProcurement>();
        for (int i = 0; i < 10; i++) {
            BillOfProcurement obj = getNewTransientBillOfProcurement(i);
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