package org.adorsys.geshotel.web;

import org.adorsys.geshotel.resto.domain.ProcurementItem;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "procurementitems", formBackingObject = ProcurementItem.class)
@RequestMapping("/procurementitems")
@Controller
public class ProcurementItemController {
}
