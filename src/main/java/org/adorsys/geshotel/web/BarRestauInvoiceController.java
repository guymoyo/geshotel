package org.adorsys.geshotel.web;

import org.adorsys.geshotel.resto.domain.BarRestauInvoice;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "barrestauinvoices", formBackingObject = BarRestauInvoice.class)
@RequestMapping("/barrestauinvoices")
@Controller
public class BarRestauInvoiceController {
}
