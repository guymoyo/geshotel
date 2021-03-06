// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.web;

import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.adorsys.geshotel.booking.domain.Invoice;
import org.adorsys.geshotel.booking.domain.Reservation;
import org.adorsys.geshotel.booking.domain.ReservationState;
import org.adorsys.geshotel.booking.domain.Room;
import org.adorsys.geshotel.domain.Customer;
import org.adorsys.geshotel.domain.UserAccount;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

privileged aspect ReservationController_Roo_Controller {
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String ReservationController.createForm(Model uiModel) {
        uiModel.addAttribute("reservation", new Reservation());
        addDateTimeFormatPatterns(uiModel);
        List dependencies = new ArrayList();
        if (Room.countRooms() == 0) {
            dependencies.add(new String[]{"room", "rooms"});
        }
        uiModel.addAttribute("dependencies", dependencies);
        return "reservations/create";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String ReservationController.show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("reservation", Reservation.findReservation(id));
        uiModel.addAttribute("itemId", id);
        return "reservations/show";
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String ReservationController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("reservations", Reservation.findReservationEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Reservation.countReservations() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("reservations", Reservation.findAllReservations());
        }
        addDateTimeFormatPatterns(uiModel);
        return "reservations/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String ReservationController.update(@Valid Reservation reservation, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("reservation", reservation);
            addDateTimeFormatPatterns(uiModel);
            return "reservations/update";
        }
        uiModel.asMap().clear();
        reservation.merge();
        return "redirect:/reservations/" + encodeUrlPathSegment(reservation.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String ReservationController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("reservation", Reservation.findReservation(id));
        addDateTimeFormatPatterns(uiModel);
        return "reservations/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String ReservationController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Reservation.findReservation(id).remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/reservations";
    }
    
    @ModelAttribute("invoices")
    public Collection<Invoice> ReservationController.populateInvoices() {
        return Invoice.findAllInvoices();
    }
    
    @ModelAttribute("reservations")
    public Collection<Reservation> ReservationController.populateReservations() {
        return Reservation.findAllReservations();
    }
    
    @ModelAttribute("rooms")
    public Collection<Room> ReservationController.populateRooms() {
        return Room.findAllRooms();
    }
    
    @ModelAttribute("customers")
    public Collection<Customer> ReservationController.populateCustomers() {
        return Customer.findAllCustomers();
    }
    
    @ModelAttribute("useraccounts")
    public Collection<UserAccount> ReservationController.populateUserAccounts() {
        return UserAccount.findAllUserAccounts();
    }
    
    void ReservationController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("reservation_checkindate_date_format", "dd-MM-yyyy");
        uiModel.addAttribute("reservation_checkoutdate_date_format", "dd-MM-yyyy");
        uiModel.addAttribute("reservation_creationdate_date_format", "dd-MM-yyyy HH:mm:ss");
        uiModel.addAttribute("reservation_mincreationdate_date_format", "dd-MM-yyyy HH:mm:ss");
        uiModel.addAttribute("reservation_maxcreationdate_date_format", "dd-MM-yyyy HH:mm:ss");
    }
    
}
