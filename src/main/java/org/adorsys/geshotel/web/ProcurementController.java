package org.adorsys.geshotel.web;

import org.adorsys.geshotel.resto.domain.Procurement;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "procurements", formBackingObject = Procurement.class)
@RequestMapping("/procurements")
@Controller
public class ProcurementController {
}
