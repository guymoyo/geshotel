package org.adorsys.geshotel.web;

import org.adorsys.geshotel.resto.domain.BarRestauPayment;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "barrestaupayments", formBackingObject = BarRestauPayment.class)
@RequestMapping("/barrestaupayments")
@Controller
public class BarRestauPaymentController {
}
