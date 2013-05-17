package org.adorsys.geshotel.resto.work.beans;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.adorsys.geshotel.booking.domain.Invoice;
import org.adorsys.geshotel.booking.domain.InvoiceState;
import org.adorsys.geshotel.booking.domain.Reservation;
import org.adorsys.geshotel.booking.domain.Room;
import org.adorsys.geshotel.domain.Customer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.util.Assert;

@RooJavaBean
@RooToString
public class HostedCustomerCmd{
//	private Long roomId;
//	private String customerLastName;
//	private Long customerId;
	
	@NotNull
	private Room room ;
	@NotNull
	private String saleKey ;
	private Customer customer ;
	private List<Room> occupiedRooms = new ArrayList<Room>();
	private List<Customer> activeCustomers = new ArrayList<Customer>();
	
	public HostedCustomerCmd(){
		List<Invoice> invoices = Invoice.findInvoicesByInvoiceState(InvoiceState.Ouvert).getResultList();
		this.occupiedRooms =Room.findOccupiedRoomsByInvoices(invoices);
		this.activeCustomers=Customer.findActiveCustomerByInvoice(invoices); 
	}
	public HostedCustomerCmd(List<Room> occupiedRooms,
			List<Customer> activeCustomers) {
		super();
		this.occupiedRooms = occupiedRooms;
		this.activeCustomers = activeCustomers;
	}
	
	public boolean checkHostedCustomer(Customer customer,Room room){
		boolean result = false ;
		if(occupiedRooms.contains(room)) result = true;
		if(result) 
			return checkCustomerByLastName(activeCustomers, customer);
		return false;
	}
	public boolean checkHostedCustomer(){
		List<Invoice> invoices = Invoice.findInvoicesByInvoiceState(InvoiceState.Ouvert).getResultList();
		for (Invoice invoice : invoices) {
			boolean roomFounded = false ;
			boolean customerFounded = false ;
			List<Reservation> reservationByCustomer = Reservation.findReservationsByCustomer(invoice.getCustomer()).getResultList();
			for (Reservation reservation : reservationByCustomer) {
				//si la chambre existe dans aumoins une des reservation de cette facture. 
				if(this.room.getId().equals(reservation.getRoom().getId()))  {
					roomFounded = true ;
					break ;
				}
			}
			//si le client existe dans aumoins une des reservations.
			for (Reservation reservation : reservationByCustomer) {
				if(this.customer.getId().equals(reservation.getCustomer().getId()))	{
					customerFounded = true;
					break ;
				}
			}
			if((roomFounded==true) && (customerFounded==true)) return true;
		}
		return false ;
	}
	private boolean checkCustomerByLastName(List<Customer> customers,Customer customer){
		Assert.notNull(customers, "At least one customer is required, found : null");
		Assert.notNull(customer,"give a customer to check");
		for (Customer customerVar : customers) {
			if(StringUtils.equalsIgnoreCase(customerVar.getLastName(), customer.getLastName())){
				return true ;
			}
		}
		return false;
	}
	
}
