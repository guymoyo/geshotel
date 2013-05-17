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
import org.adorsys.geshotel.resto.domain.KitchenOrder;
import org.adorsys.geshotel.resto.domain.KitchenOrderDataOnDemand;
import org.adorsys.geshotel.resto.domain.KitchenOrderItem;
import org.adorsys.geshotel.resto.domain.Product;
import org.adorsys.geshotel.resto.domain.ProductDataOnDemand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

privileged aspect KitchenOrderItemDataOnDemand_Roo_DataOnDemand {
    
    declare @type: KitchenOrderItemDataOnDemand: @Component;
    
    private Random KitchenOrderItemDataOnDemand.rnd = new SecureRandom();
    
    private List<KitchenOrderItem> KitchenOrderItemDataOnDemand.data;
    
    @Autowired
    private KitchenOrderDataOnDemand KitchenOrderItemDataOnDemand.kitchenOrderDataOnDemand;
    
    @Autowired
    private ProductDataOnDemand KitchenOrderItemDataOnDemand.productDataOnDemand;
    
    public KitchenOrderItem KitchenOrderItemDataOnDemand.getNewTransientKitchenOrderItem(int index) {
        KitchenOrderItem obj = new KitchenOrderItem();
        setDateOfOrdering(obj, index);
        setKitchenOrder(obj, index);
        setKitchenOrderItemKey(obj, index);
        setProduct(obj, index);
        setQty(obj, index);
        return obj;
    }
    
    public void KitchenOrderItemDataOnDemand.setDateOfOrdering(KitchenOrderItem obj, int index) {
        Date dateOfOrdering = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setDateOfOrdering(dateOfOrdering);
    }
    
    public void KitchenOrderItemDataOnDemand.setKitchenOrder(KitchenOrderItem obj, int index) {
        KitchenOrder kitchenOrder = kitchenOrderDataOnDemand.getRandomKitchenOrder();
        obj.setKitchenOrder(kitchenOrder);
    }
    
    public void KitchenOrderItemDataOnDemand.setKitchenOrderItemKey(KitchenOrderItem obj, int index) {
        String kitchenOrderItemKey = "kitchenOrderItemKey_" + index;
        obj.setKitchenOrderItemKey(kitchenOrderItemKey);
    }
    
    public void KitchenOrderItemDataOnDemand.setProduct(KitchenOrderItem obj, int index) {
        Product product = productDataOnDemand.getRandomProduct();
        obj.setProduct(product);
    }
    
    public void KitchenOrderItemDataOnDemand.setQty(KitchenOrderItem obj, int index) {
        BigDecimal qty = BigDecimal.valueOf(index);
        obj.setQty(qty);
    }
    
    public KitchenOrderItem KitchenOrderItemDataOnDemand.getSpecificKitchenOrderItem(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        KitchenOrderItem obj = data.get(index);
        return KitchenOrderItem.findKitchenOrderItem(obj.getId());
    }
    
    public KitchenOrderItem KitchenOrderItemDataOnDemand.getRandomKitchenOrderItem() {
        init();
        KitchenOrderItem obj = data.get(rnd.nextInt(data.size()));
        return KitchenOrderItem.findKitchenOrderItem(obj.getId());
    }
    
    public boolean KitchenOrderItemDataOnDemand.modifyKitchenOrderItem(KitchenOrderItem obj) {
        return false;
    }
    
    public void KitchenOrderItemDataOnDemand.init() {
        data = KitchenOrderItem.findKitchenOrderItemEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'KitchenOrderItem' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<org.adorsys.geshotel.resto.domain.KitchenOrderItem>();
        for (int i = 0; i < 10; i++) {
            KitchenOrderItem obj = getNewTransientKitchenOrderItem(i);
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
