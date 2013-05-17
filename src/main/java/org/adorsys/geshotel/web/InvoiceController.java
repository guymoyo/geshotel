package org.adorsys.geshotel.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.adorsys.geshotel.booking.domain.Invoice;
import org.adorsys.geshotel.booking.domain.InvoiceItem;
import org.adorsys.geshotel.booking.domain.InvoiceState;
import org.adorsys.geshotel.booking.domain.Reservation;
import org.adorsys.geshotel.domain.Hotel;
import org.adorsys.geshotel.domain.Payment;
import org.adorsys.geshotel.utility.ErrorMessage;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RooWebScaffold(path = "invoices", formBackingObject = Invoice.class)
@RequestMapping("/invoices")
@Controller
public class InvoiceController {
	
	/*@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String InvoiceController(@PathVariable("id") Long id, Model uiModel) {
		
		return "redirect:/invoices/facture?invoiceId="+id;
    }*/
	
	 @RequestMapping("/facture")
	 public String facture(@RequestParam(value = "reservationId", required = false) Long id,
			 @RequestParam(value = "invoiceId", required = false) Long inv, Model uiModel) {
		
		 Invoice invoice;
		try {
			Reservation reservation = Reservation.findReservation(id);
			 invoice = Invoice.findInvoice(reservation.getInvoice().getId());
		} catch (Exception e) {
			invoice = Invoice.findInvoice(inv);
		}
		 
		 List<InvoiceItem> list = InvoiceItem.findInvoiceItemsByInvoice(invoice).getResultList();
		 List<Reservation> list2 = Reservation.findReservationsByInvoice(invoice).getResultList();
		 List<Payment> list3 = Payment.findPaymentsByInvoice(invoice).getResultList();
		 
		 uiModel.addAttribute("invoice", invoice);
		 try {
			Hotel hotel = Hotel.findAllHotels().get(0);
			uiModel.addAttribute("hotel", hotel);
		} catch (Exception e) {
			uiModel.addAttribute("warn", new ErrorMessage("renseigner les informations de l'hotel", "set information of hotel").getMessage());
		}
		
		 uiModel.addAttribute("customer", invoice.getCustomer());
		 uiModel.addAttribute("items", list);
		 uiModel.addAttribute("reservations", list2);
		 uiModel.addAttribute("payments", list3);
		 
		 return "invoices/facture";
	 }
	 
	 @RequestMapping("/close")
	    public String closeInvoice(Model uiModel, HttpServletRequest request, HttpSession session, @RequestParam(value = "id") long id) {
	        Invoice invoice = Invoice.findInvoice(id);
	        invoice.setInvoiceState(InvoiceState.Ferme);
	        invoice.merge();
	        invoice.flush();
	        return "redirect:/invoices/facture?invoiceId=" + invoice.getId();
	    }
}
