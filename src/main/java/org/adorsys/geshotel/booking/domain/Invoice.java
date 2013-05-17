package org.adorsys.geshotel.booking.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.adorsys.geshotel.domain.Customer;
import org.adorsys.geshotel.domain.Hotel;
import org.adorsys.geshotel.domain.Payment;
import org.adorsys.geshotel.domain.UserAccount;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findInvoicesByInvoiceState", "findInvoicesByCustomer", "findInvoicesByPayementState", "findInvoicesByCustomerAndInvoiceState" })
public class Invoice {

    @ManyToOne
    private Customer customer;

    @Transient
    private BigDecimal invoiceAmount = BigDecimal.ZERO;

    @Transient
    private BigDecimal valueAdddedTax = BigDecimal.ZERO;

    private BigDecimal otherTaxes = BigDecimal.ZERO;

    @Transient
    private BigDecimal amountDue = BigDecimal.ZERO;

    @Transient
    private BigDecimal amountPaid = BigDecimal.ZERO;

    @Transient
    private BigDecimal reste = BigDecimal.ZERO;

    @Transient
    private BigDecimal montantService = BigDecimal.ZERO;

    @Enumerated
    private PayementState payementState;

    @Enumerated
    private InvoiceState invoiceState;

    @ManyToOne
    private UserAccount originator;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH :mm:ss")
    private Date dateOfClose;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH :mm:ss")
    private Date dateOfCreate;

    public Invoice(Customer customer, PayementState payementState, InvoiceState invoiceState, UserAccount originator, Date dateOfCreate) {
        super();
        this.customer = customer;
        this.payementState = payementState;
        this.invoiceState = invoiceState;
        this.originator = originator;
        this.dateOfCreate = dateOfCreate;
    }

    private void calculInvoiceAmount() {
        Invoice invoice = Invoice.findInvoice(this.getId());
        List<Reservation> list = Reservation.findReservationsByInvoice(invoice).getResultList();
        this.invoiceAmount = BigDecimal.ZERO;
        for (Reservation r : list) {
            this.invoiceAmount = this.invoiceAmount.add(r.getTotalAmount());
        }
        List<InvoiceItem> list2 = InvoiceItem.findInvoiceItemsByInvoice(invoice).getResultList();
        for (InvoiceItem item : list2) {
            BigDecimal augend = item.getAmount();
            this.invoiceAmount = this.invoiceAmount.add(augend);
            if (augend == null) augend = BigDecimal.ZERO;
            if (this.montantService == null) this.montantService = BigDecimal.ZERO;
            this.montantService = this.montantService.add(augend);
        }
    }

    private void calculTax() {
        BigDecimal tva = BigDecimal.ZERO;
        try {
            tva = Hotel.findAllHotels().get(0).getTVA();
        } catch (Exception e) {
            Logger log = Logger.getLogger("Invoice");
            log.info("tva = Hotel.findAllHotels().get(0).getTVA();");
        }
        if (tva == null) tva = BigDecimal.ZERO;
        this.valueAdddedTax = this.invoiceAmount.multiply(tva).divide(new BigDecimal(100));
    }

    private void calculAmountDue() {
        this.amountDue = BigDecimal.ZERO;
        this.amountDue = this.amountDue.add(this.invoiceAmount).add(this.valueAdddedTax).add(this.otherTaxes);
    }

    private void AmountPaid() {
        List<Payment> list = Payment.findPaymentsByInvoice(this).getResultList();
        this.amountPaid = BigDecimal.ZERO;
        if (list != null && !list.isEmpty()) {
            for (Payment p : list) {
                this.amountPaid = this.amountPaid.add(p.getAmount());
            }
        }
    }

    private void reste() {
        this.reste = this.amountDue.add(this.amountPaid.negate());
    }

    @PostLoad
    public void postLoadFunction() {
        calculInvoiceAmount();
        calculTax();
        calculAmountDue();
        AmountPaid();
        reste();
    }
}
