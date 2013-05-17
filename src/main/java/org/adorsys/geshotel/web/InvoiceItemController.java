package org.adorsys.geshotel.web;

import java.util.List;

import org.adorsys.geshotel.booking.domain.Invoice;
import org.adorsys.geshotel.booking.domain.InvoiceItem;
import org.adorsys.geshotel.booking.domain.Reservation;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RooWebScaffold(path = "invoiceitems", formBackingObject = InvoiceItem.class)
@RequestMapping("/invoiceitems")
@Controller
public class InvoiceItemController {
	
	
}
