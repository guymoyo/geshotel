// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.resto.domain;

import java.lang.String;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.adorsys.geshotel.resto.domain.BarRestauState;
import org.adorsys.geshotel.resto.domain.Product;
import org.adorsys.geshotel.resto.domain.ProductType;
import org.adorsys.geshotel.resto.domain.ProductTypeDataOnDemand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

privileged aspect ProductDataOnDemand_Roo_DataOnDemand {
    
    declare @type: ProductDataOnDemand: @Component;
    
    private Random ProductDataOnDemand.rnd = new SecureRandom();
    
    private List<Product> ProductDataOnDemand.data;
    
    @Autowired
    private ProductTypeDataOnDemand ProductDataOnDemand.productTypeDataOnDemand;
    
    public Product ProductDataOnDemand.getNewTransientProduct(int index) {
        Product obj = new Product();
        setDescription(obj, index);
        setEmployeePrice(obj, index);
        setPrice(obj, index);
        setProductKey(obj, index);
        setProductState(obj, index);
        setProductType(obj, index);
        setQty(obj, index);
        setTitle(obj, index);
        return obj;
    }
    
    public void ProductDataOnDemand.setDescription(Product obj, int index) {
        String description = "description_" + index;
        obj.setDescription(description);
    }
    
    public void ProductDataOnDemand.setEmployeePrice(Product obj, int index) {
        BigDecimal employeePrice = BigDecimal.valueOf(index);
        obj.setEmployeePrice(employeePrice);
    }
    
    public void ProductDataOnDemand.setPrice(Product obj, int index) {
        BigDecimal price = BigDecimal.valueOf(index);
        obj.setPrice(price);
    }
    
    public void ProductDataOnDemand.setProductKey(Product obj, int index) {
        String productKey = "productKey_" + index;
        obj.setProductKey(productKey);
    }
    
    public void ProductDataOnDemand.setProductState(Product obj, int index) {
        BarRestauState productState = BarRestauState.class.getEnumConstants()[0];
        obj.setProductState(productState);
    }
    
    public void ProductDataOnDemand.setProductType(Product obj, int index) {
        ProductType productType = productTypeDataOnDemand.getRandomProductType();
        obj.setProductType(productType);
    }
    
    public void ProductDataOnDemand.setQty(Product obj, int index) {
        BigDecimal qty = BigDecimal.valueOf(index);
        obj.setQty(qty);
    }
    
    public void ProductDataOnDemand.setTitle(Product obj, int index) {
        String title = "title_" + index;
        obj.setTitle(title);
    }
    
    public Product ProductDataOnDemand.getSpecificProduct(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        Product obj = data.get(index);
        return Product.findProduct(obj.getId());
    }
    
    public Product ProductDataOnDemand.getRandomProduct() {
        init();
        Product obj = data.get(rnd.nextInt(data.size()));
        return Product.findProduct(obj.getId());
    }
    
    public boolean ProductDataOnDemand.modifyProduct(Product obj) {
        return false;
    }
    
    public void ProductDataOnDemand.init() {
        data = Product.findProductEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Product' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<org.adorsys.geshotel.resto.domain.Product>();
        for (int i = 0; i < 10; i++) {
            Product obj = getNewTransientProduct(i);
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
