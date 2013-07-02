package org.adorsys.geshotel.booking.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.ui.Model;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import org.springframework.format.annotation.DateTimeFormat;
import org.adorsys.geshotel.booking.domain.Room;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import javax.validation.constraints.Size;
import org.adorsys.geshotel.booking.domain.ReservationState;
import javax.persistence.Enumerated;
import org.adorsys.geshotel.booking.domain.InvoiceItem;
import org.adorsys.geshotel.domain.Customer;
import org.adorsys.geshotel.domain.UserAccount;
import org.adorsys.geshotel.utility.DateUtility;
import org.adorsys.geshotel.utility.ErrorMessage;

@RooJson
@RooJavaBean
@RooToString
@RooEntity(finders = { "findReservationsByInvoice", "findReservationsByCustomer", "findReservationsByCreationDateBetween" })
public class Reservation {

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

    // Champ autrefois transcient
    private BigDecimal totalAmount = BigDecimal.ZERO;

    private int numDays;

    // champ autrefois transcient
    private int discount;
    

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date creationDate;

    @ManyToOne
    private UserAccount receipt;

    private int numOfPeople = 1;
    
    @PrePersist
    public void prePersist(){
    	calculAmount();
        calculTotalAmount();
    }

    private BigDecimal deposit = BigDecimal.ZERO;

    public static List<Reservation> getReservationByDay(Room room, Date dateIn) {
        EntityManager em = Reservation.entityManager();
        TypedQuery<Reservation> query = em.createQuery("Select r From Reservation As r where r.room = :roomvalue  And :dateIn >= r.checkInDate And :dateIn <= r.checkOutDate ", Reservation.class);
        query.setParameter("dateIn", dateIn);
        query.setParameter("roomvalue", room);
        return query.getResultList();
    }
    
    public static List<Reservation> getReservationByDayState(Room room, Date dateIn) {
        EntityManager em = Reservation.entityManager();
        ReservationState state = ReservationState.LIBERE;
        TypedQuery<Reservation> query = em.createQuery("Select r From Reservation As r where r.room = :roomvalue  And :dateIn >= r.checkInDate And :dateIn <= r.checkOutDate And r.reservationState != :stateValue ", Reservation.class);
        query.setParameter("dateIn", dateIn);
        query.setParameter("roomvalue", room);
        query.setParameter("stateValue", state);
        return query.getResultList();
    }
    
    public static List<Reservation> getReservationOccuped(Date dateIn) {
        EntityManager em = Reservation.entityManager();
        ReservationState state = ReservationState.OCCUPE;
        TypedQuery<Reservation> query = em.createQuery("Select r From Reservation As r where r.reservationState = :stateValue  And :dateIn >= r.checkInDate And :dateIn <= r.checkOutDate ", Reservation.class);
        query.setParameter("dateIn", dateIn);
        query.setParameter("stateValue", state);
        return query.getResultList();
    }
    
    public static List<Reservation> reservationWithInvoiceClose() {
    	InvoiceState state = InvoiceState.Ouvert;
        EntityManager em = Reservation.entityManager();
        TypedQuery<Reservation> query = em.createQuery("Select r From Reservation As r where r.invoice != null  And r.invoice.invoiceState = :stateValue ", Reservation.class);
        query.setParameter("stateValue", state);
        return query.getResultList();
    }

    public Reservation(Date checkInDate, Date checkOutDate, Room room, BigDecimal amount, BigDecimal reduction, String complement, Customer customer, UserAccount receipt, ReservationState reservationState, BigDecimal otherValue) {
        super();
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.room = room;
        this.amount = amount;
        this.reduction = reduction;
        this.complement = complement;
        this.customer = customer;
        this.receipt = receipt;
        this.reservationState = reservationState;
        this.otherValue = otherValue;
    }

    public Reservation() {
        super();
    }

    public boolean isNonConfirmed() {
        if (this.reservationState == ReservationState.NONCONFIRME) return true; else return false;
    }

    public boolean verif(Model uiModel) {
        boolean bool = true;
        int nbreOfDay = DateUtility.diff(this.checkInDate, this.checkOutDate);
        for (int i = 0; i <= nbreOfDay; i++) {
            List<Reservation> list = getReservationByDay(this.room, DateUtility.addDayToDate(this.checkInDate, i));
            if (list != null && !list.isEmpty()) {
            	if(!list.get(0).getReservationState().equals(ReservationState.LIBERE)){
            	
            		uiModel.addAttribute("error1", new ErrorMessage("une resevation est deja faite a cette date" , "you can not book of this plage of date").getMessage());
                    bool = false;
                    break;
            	}
                
            }
        }
        if (this.amount.add(reduction.negate()).compareTo(this.room.getRoomCategory().getPriceMin()) == -1) {
            bool = false;
            BigDecimal priceMin = this.room.getRoomCategory().getPriceMin();
            uiModel.addAttribute("error2", new ErrorMessage("le prix ne peut pas etre inferieur a: " + priceMin.longValue(), "the price can not be inferior to: " + priceMin.longValue()).getMessage());
        }
        return bool;
    }

    private void calculAmount() {
        this.amount = this.room.getRoomCategory().getPrice().add(this.reduction.negate());
    }

    public void calculTotalAmount() {
        this.totalAmount = this.amount.multiply(BigDecimal.valueOf(this.numDays)).add(this.otherValue);
    }

    public static boolean verifCollision(Date out, int nbdays, Model uiModel, Room room) {
        boolean bool = true;
        for (int i = 0; i < nbdays; i++) {
            List<Reservation> list = getReservationByDay(room, DateUtility.addDayToDate(out, i));
            if (list != null && !list.isEmpty()) {
                uiModel.addAttribute("error1", new ErrorMessage("vous ne pouvez reservez sur cette date car elle entre en collision avec: " + list.get(0), "you can not book of this plage of date, because: " + list.get(0)).getMessage());
                bool = false;
                break;
            }
        }
        return bool;
    }

    public void testPeople(){
    	if(this.numOfPeople < 1)
    		this.numOfPeople = 1;
    }
    
    @PostLoad
    private void postloadMethod() {
        calculAmount();
        calculTotalAmount();
        testPeople();
    }
}
