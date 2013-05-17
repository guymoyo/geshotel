package org.adorsys.geshotel.web;

import org.adorsys.geshotel.resto.domain.Supplier;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "suppliers", formBackingObject = Supplier.class)
@RequestMapping("/suppliers")
@Controller
public class SupplierController {
}
