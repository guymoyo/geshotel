package org.adorsys.geshotel.web;

import org.adorsys.geshotel.resto.domain.SaleItem;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "saleitems", formBackingObject = SaleItem.class)
@RequestMapping("/saleitems")
@Controller
public class SaleItemController {
}
