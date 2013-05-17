package org.adorsys.geshotel.booking.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import org.adorsys.geshotel.booking.domain.Invoice;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findInvoiceItemsByInvoice" })
public class InvoiceItem {

    private String itemNumber;
    
    private String itemName;

    @NotNull
    private BigDecimal qty;

    @NotNull
    private BigDecimal unitPrice;

    @NotNull
    private BigDecimal amount;
    /**
     * Product description
     */
    private String itemDesc;

    @ManyToOne
    private Invoice invoice;

    

    private void calculTotalAmount() {
    	
        this.amount = qty.multiply(unitPrice);
    }

    @PostLoad
    private void postloadMethod() {
        calculTotalAmount();
    }
}
