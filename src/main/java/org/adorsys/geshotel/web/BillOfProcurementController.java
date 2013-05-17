package org.adorsys.geshotel.web;

import org.adorsys.geshotel.resto.domain.BillOfProcurement;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "billofprocurements", formBackingObject = BillOfProcurement.class)
@RequestMapping("/billofprocurements")
@Controller
public class BillOfProcurementController {
}
