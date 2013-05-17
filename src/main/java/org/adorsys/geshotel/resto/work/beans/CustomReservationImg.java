package org.adorsys.geshotel.resto.work.beans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.adorsys.geshotel.booking.domain.Invoice;
import org.adorsys.geshotel.booking.domain.ReservationState;
import org.adorsys.geshotel.booking.domain.Room;
import org.adorsys.geshotel.domain.Customer;
import org.adorsys.geshotel.domain.UserAccount;
import org.adorsys.geshotel.resto.domain.ProductGroup;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
@RooJavaBean
@RooToString
public class CustomReservationImg {
	private Long id ;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date checkInDate;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date checkOutDate;

    @NotNull
    @ManyToOne
    private Room room;

    @NotNull
    private BigDecimal amount = BigDecimal.ZERO;

    private BigDecimal reduction = BigDecimal.ZERO;

    private BigDecimal otherValue = BigDecimal.ZERO;

    @Size(max = 255)
    private String complement;

    @Enumerated
    private ReservationState reservationState;

    @ManyToOne(fetch = FetchType.LAZY)
    private Invoice invoice;

    @ManyToOne
    private Customer customer;

    @Transient
    private BigDecimal totalAmount = BigDecimal.ZERO;

    private int numDays;

    @Transient
    private int discount;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date creationDate;

    @ManyToOne
    private UserAccount receipt;

    private int numOfPeople = 1;

    private BigDecimal deposit = BigDecimal.ZERO;
    private List<GroupAndSaleReport> groupAndSaleReports = new ArrayList<GroupAndSaleReport>();
	private BigDecimal groupAndSaleReportAmount = BigDecimal.ZERO;
	public static GroupAndSaleReport findAGroupReport(CustomReservationImg allGroupAndSaleReport,ProductGroup group){
		List<GroupAndSaleReport> groupAndSaleReports2 = allGroupAndSaleReport.getGroupAndSaleReports();
		for (GroupAndSaleReport groupAndSaleReport : groupAndSaleReports2) {
			if(groupAndSaleReport.getProductGroup().getId().equals(group.getId())) return groupAndSaleReport;
		}
		return null;
	}
}
