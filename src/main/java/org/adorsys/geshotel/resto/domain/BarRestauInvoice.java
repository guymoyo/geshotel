package org.adorsys.geshotel.resto.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;
import org.adorsys.geshotel.resto.domain.Sale;
import javax.persistence.ManyToOne;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import org.adorsys.geshotel.resto.domain.CustomerType;
import javax.persistence.Enumerated;
import org.adorsys.geshotel.booking.domain.InvoiceState;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findBarRestauInvoicesByBarRestauInvoiceKeyEquals", "findBarRestauInvoicesByCreationDateBetween", "findBarRestauInvoicesByInvoiceNumberEquals", "findBarRestauInvoicesBySale", "findBarRestauInvoicesByInvoiceState" })
public class BarRestauInvoice {

    @NotNull
    @Column(name = "barRestauInvoiceKeyBk", unique = true)
    private String barRestauInvoiceKey;

    @NotNull
    @ManyToOne
    private Sale sale;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date creationDate;

    private String invoicePath;

    private String note;

    @Enumerated
    private CustomerType customerType;

    @Enumerated
    private InvoiceState invoiceState;

    @NotNull
    @Column(unique = true)
    private String invoiceNumber;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((barRestauInvoiceKey == null) ? 0 : barRestauInvoiceKey.hashCode());
        result = prime * result + ((invoiceNumber == null) ? 0 : invoiceNumber.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        BarRestauInvoice other = (BarRestauInvoice) obj;
        if (barRestauInvoiceKey == null) {
            if (other.barRestauInvoiceKey != null) return false;
        } else if (!barRestauInvoiceKey.equals(other.barRestauInvoiceKey)) return false;
        if (invoiceNumber == null) {
            if (other.invoiceNumber != null) return false;
        } else if (!invoiceNumber.equals(other.invoiceNumber)) return false;
        return true;
    }
}
