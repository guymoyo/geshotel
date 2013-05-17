package org.adorsys.geshotel.web;

import org.adorsys.geshotel.resto.domain.KitchenOrder;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "kitchenorders", formBackingObject = KitchenOrder.class)
@RequestMapping("/kitchenorders")
@Controller
public class KitchenOrderController {
}
