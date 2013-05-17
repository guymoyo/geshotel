package org.adorsys.geshotel.resto.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import org.adorsys.geshotel.domain.Customer;
import org.adorsys.geshotel.resto.work.beans.AllGroupAndSaleReport;
import org.adorsys.geshotel.resto.work.beans.CustomReservationImg;
import org.adorsys.geshotel.resto.work.beans.GroupAndSaleReport;
import org.springframework.aop.aspectj.AspectJAdviceParameterNameDiscoverer.AmbiguousBindingException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.adorsys.geshotel.booking.domain.Invoice;
import org.adorsys.geshotel.booking.domain.InvoiceItem;
import org.adorsys.geshotel.booking.domain.InvoiceState;
import org.adorsys.geshotel.booking.domain.Reservation;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findSalesBySaleKeyEquals", "findSalesByRecordDateBetweenAndSaleState", "findSalesByRecordDateBetweenAndSaleStateAndCustomerType", "findSalesBySaleState", "findSalesBySaleStateAndRecordDateBetween", "findSalesByCustomer", "findSalesByInvoice", "findSalesByCustomerAndInvoice" })
public class Sale {
	public static final Logger log = Logger.getLogger("sale_log");
    @NotNull
    @Column(unique = true)
    private String saleKey;

    private String note;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<SaleItem> saleItem = new HashSet<SaleItem>();

    @NotNull
    private BigDecimal totalAmount;

    private BigDecimal tax;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date recordDate;

    private String userNameDescription;

    @ManyToOne
    private BrTable brTable;

    @ManyToOne
    private Customer customer;

    @Enumerated
    private CustomerType customerType;

    @Enumerated
    private BarRestauState saleState;

    @ManyToOne
    private Invoice invoice;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((saleKey == null) ? 0 : saleKey.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Sale other = (Sale) obj;
        if (saleKey == null) {
            if (other.saleKey != null) return false;
        } else if (!saleKey.equals(other.saleKey)) return false;
        return true;
    }

    public List<BarRestauInvoice> findBarRestauInvoice() {
        return BarRestauInvoice.findBarRestauInvoicesBySale(this).getResultList();
    }

    public boolean invoiceGenerated() {
        List<BarRestauInvoice> barRestauInvoice = findBarRestauInvoice();
        if (barRestauInvoice != null && !barRestauInvoice.isEmpty()) return true;
        return false;
    }

    public static List<Sale> findCustomerSalesByInvoiceStates(Customer customer, InvoiceState invoiceState) {
        String request = "SELECT sale FROM Sale AS sale, Invoice AS invoice WHERE invoice.customer = :customer AND " + "invoice.invoiceState = :invoiceState";
        List<Sale> sales = new ArrayList<Sale>();
        List<Invoice> invoices = Invoice.findInvoicesByCustomerAndInvoiceState(customer, invoiceState).getResultList();
        for (Invoice invoice : invoices) {
        	List<Sale> resultList = Sale.findSalesByInvoice(invoice).getResultList();
        	sales.addAll(resultList);
        }
        log.log(Level.INFO, "sales "+sales);
        return sales;
    }

    public List<GroupAndSaleReport> findServicesAndSaleItems(CustomReservationImg customReservationImg) {
        if (customReservationImg == null) throw new NullPointerException("Null Value Passed");
        List<SaleItem> saleItems = SaleItem.findSaleItemsBySale(this).getResultList();
        log.log(Level.INFO, "Sales Items : "+saleItems);
        ArrayList<ProductGroup> foundGroups = new ArrayList<ProductGroup>();
        List<GroupAndSaleReport> groupAndSaleReports = new ArrayList<GroupAndSaleReport>();
        for (SaleItem saleItem : saleItems) {
            ProductGroup group = saleItem.findProductGroup();
            if (foundGroups.contains(group)) {
                GroupAndSaleReport findGroupInGroupAndSaleReports = CustomReservationImg.findAGroupReport(customReservationImg, group);
                findGroupInGroupAndSaleReports.getSaleItems().add(saleItem);
                findGroupInGroupAndSaleReports.getServiceAmount().add(saleItem.getAmount());
            } else {
                GroupAndSaleReport groupAndSaleReport = new GroupAndSaleReport();
                groupAndSaleReport.setProductGroup(group);
                groupAndSaleReport.getSaleItems().add(saleItem);
                groupAndSaleReport.setServiceAmount(groupAndSaleReport.getServiceAmount().add(saleItem.getAmount()));
                foundGroups.add(group);
                List<GroupAndSaleReport> groupAndSaleReports2 = customReservationImg.getGroupAndSaleReports();
                groupAndSaleReports2.add(groupAndSaleReport);
                customReservationImg.setGroupAndSaleReports(groupAndSaleReports2);
                groupAndSaleReports.add(groupAndSaleReport);
            }
        }
        return groupAndSaleReports;
    }

    public GroupAndSaleReport findGroupInGroupAndSaleReports(List<GroupAndSaleReport> groupAndSaleReports, ProductGroup productGroup) {
        for (GroupAndSaleReport groupAndSaleReport : groupAndSaleReports) {
            if (groupAndSaleReport.getProductGroup().getId().equals(productGroup.getId())) return groupAndSaleReport;
        }
        return null;
    }
}
