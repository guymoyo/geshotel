// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.resto.work.beans;

import java.math.BigDecimal;
import java.util.List;
import org.adorsys.geshotel.resto.domain.ProductGroup;
import org.adorsys.geshotel.resto.domain.SaleItem;

privileged aspect GroupAndSaleReport_Roo_JavaBean {
    
    public ProductGroup GroupAndSaleReport.getProductGroup() {
        return this.productGroup;
    }
    
    public void GroupAndSaleReport.setProductGroup(ProductGroup productGroup) {
        this.productGroup = productGroup;
    }
    
    public List<SaleItem> GroupAndSaleReport.getSaleItems() {
        return this.saleItems;
    }
    
    public void GroupAndSaleReport.setSaleItems(List<SaleItem> saleItems) {
        this.saleItems = saleItems;
    }
    
    public BigDecimal GroupAndSaleReport.getServiceAmount() {
        return this.serviceAmount;
    }
    
    public void GroupAndSaleReport.setServiceAmount(BigDecimal serviceAmount) {
        this.serviceAmount = serviceAmount;
    }
    
}
