package org.adorsys.geshotel.web;

import org.adorsys.geshotel.resto.domain.InventoryItem;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "inventoryitems", formBackingObject = InventoryItem.class)
@RequestMapping("/inventoryitems")
@Controller
public class InventoryItemController {
}
