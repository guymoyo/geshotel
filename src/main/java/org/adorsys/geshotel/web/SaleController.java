package org.adorsys.geshotel.web;

import org.adorsys.geshotel.resto.domain.Sale;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "sales", formBackingObject = Sale.class)
@RequestMapping("/sales")
@Controller
public class SaleController {
}
