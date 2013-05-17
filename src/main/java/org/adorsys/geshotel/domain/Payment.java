package org.adorsys.geshotel.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.persistence.FetchType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import org.adorsys.geshotel.booking.domain.Invoice;
import javax.persistence.ManyToOne;
import org.adorsys.geshotel.resto.domain.BillOfSale;
import org.adorsys.geshotel.resto.domain.BillOfProcurement;
import org.adorsys.geshotel.domain.PayementType;
import javax.persistence.Enumerated;
import org.adorsys.geshotel.domain.UserAccount;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findPaymentsByInvoice", "findPaymentsByDatePayemmentBetween", "findPaymentsByCasheirAndDatePayemmentBetween", "findPaymentsByCasheir" })
public class Payment {

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private Date datePayemment;

    @NotNull
    private BigDecimal Amount;

    @NotNull
    @ManyToOne(fetch=FetchType.LAZY)
    private Invoice invoice;

    private String note;

    @Enumerated
    private PayementType payementType;

    @ManyToOne
    private UserAccount casheir;
}
