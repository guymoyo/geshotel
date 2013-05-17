package org.adorsys.geshotel.booking.domain;

import java.util.ArrayList;
import java.util.List;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.util.Assert;
import org.adorsys.geshotel.booking.domain.RoomCategory;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;
import javax.validation.constraints.Size;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findRoomsByRoomCategory", "findRoomsByRoomNumber" })
public class Room {

    @ManyToOne
    private RoomCategory roomCategory;

    @NotNull
    @Column(unique = true)
    private String roomNumber;

    private String phoneNumber;

    private String coordinates;

    private String capacity;

    @Size(max = 255)
    private String description;

    public static List<Room> findOccupiedRoomsByInvoices(List<Invoice> invoices) {
        List<Room> rooms = new ArrayList<Room>();
        Assert.notNull(invoices, "Null Parameter invoices is not required");
        for (Invoice invoice : invoices) {
            Reservation reservation = Reservation.findReservationsByInvoice(invoice).getResultList().iterator().next();
            rooms.add(reservation.getRoom());
        }
        return rooms;
    }
    
    public boolean isCreated(){
    	boolean bool= true;
    	List<Room> list = Room.findRoomsByRoomNumber(this.roomNumber).getResultList();
    	if(list==null || list.isEmpty())
    		bool = false;
    	return bool;
    }
}
