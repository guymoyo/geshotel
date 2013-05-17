package org.adorsys.geshotel.web;

import org.adorsys.geshotel.resto.domain.KitchenOrderItem;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "kitchenorderitems", formBackingObject = KitchenOrderItem.class)
@RequestMapping("/kitchenorderitems")
@Controller
public class KitchenOrderItemController {
}
