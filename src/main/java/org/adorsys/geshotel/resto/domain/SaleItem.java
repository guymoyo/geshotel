package org.adorsys.geshotel.resto.domain;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.adorsys.geshotel.booking.domain.Invoice;
import org.adorsys.geshotel.booking.domain.InvoiceItem;
import org.adorsys.geshotel.domain.security.SecurityUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findSaleItemsBySale", "findSaleItemsBySaleItemKeyEquals" })
public class SaleItem {

    @NotNull
    @Column(unique = true)
    private String saleItemKey;

    @NotNull
    @ManyToOne
    private Product product;

    private BigDecimal qty;

    private BigDecimal unitPrice;

    private BigDecimal amount;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date dateOfSale;

    private String seller;

    @ManyToOne
    private Sale sale;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((saleItemKey == null) ? 0 : saleItemKey.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        SaleItem other = (SaleItem) obj;
        if (saleItemKey == null) {
            if (other.saleItemKey != null) return false;
        } else if (!saleItemKey.equals(other.saleItemKey)) return false;
        return true;
    }

    /**
     * Transform an SaleItem to an InvoiceItem.
     * @param invoice
     * @return
     */
    public InvoiceItem newInvoiceItem(Invoice invoice) {
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setAmount(BigDecimal.ZERO);
        invoiceItem.setInvoice(invoice);
        invoiceItem.setItemDesc(" Commande generee du Bar/Restaurant, Le : " + new Date());
        invoiceItem.setItemName(product.getTitle());
        invoiceItem.setItemNumber("N-" + RandomStringUtils.randomAlphanumeric(6));
        invoiceItem.setQty(qty);
        invoiceItem.setUnitPrice(unitPrice);
        return invoiceItem;
    }

    public void newInventoryItem() {
    }
    public ProductGroup findProductGroup (){
    	return product.getProductType().getProductGroup();
    }
}
