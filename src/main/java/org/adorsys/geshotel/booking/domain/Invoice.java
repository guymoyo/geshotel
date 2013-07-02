package org.adorsys.geshotel.booking.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.CascadeType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
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

    // Debut des champs transient
    
    private BigDecimal invoiceAmount = BigDecimal.ZERO;

    private BigDecimal valueAdddedTax = BigDecimal.ZERO;

    private BigDecimal otherTaxes = BigDecimal.ZERO;

    private BigDecimal amountDue = BigDecimal.ZERO;

    private BigDecimal amountPaid = BigDecimal.ZERO;

    private BigDecimal reste = BigDecimal.ZERO;

    private BigDecimal montantService = BigDecimal.ZERO;
    
    @OrderBy("datePayemment DESC")
    @OneToMany(cascade=CascadeType.ALL, mappedBy="invoice", fetch=FetchType.EAGER)
    private List<Payment> paiements= new ArrayList<Payment>();
    
    // Fin des champs transcient

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
    
    @PostLoad
    public void postLoadFunction() {
    	System.out.println("Debut du chargement");
        calculInvoiceAmount();
        calculTax();
        calculAmountDue();
        AmountPaid();
        reste();
    }
    
// Calcul du montant total de la facture
    public void calculInvoiceAmount() {
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

    
    // Calcul de la taxe
    public void calculTax() {
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

    // Calcul du montant a payer de la facture
    public void calculAmountDue() {
        this.amountDue = BigDecimal.ZERO;
        this.amountDue = this.amountDue.add(this.invoiceAmount).add(this.valueAdddedTax).add(this.otherTaxes);
    }

    // Calcul du montant paye de la facture
    public void AmountPaid() {
        List<Payment> list = Payment.findPaymentsByInvoice(this).getResultList();
        this.amountPaid = BigDecimal.ZERO;
        if (list != null && !list.isEmpty()) {
            for (Payment p : list) {
                this.amountPaid = this.amountPaid.add(p.getAmount());
            }
        }
    }

    // Calcul du reste de la facture
    public void reste() {
        this.reste = this.amountDue.add(this.amountPaid.negate());
    }

}
