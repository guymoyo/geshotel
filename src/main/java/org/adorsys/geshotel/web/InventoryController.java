package org.adorsys.geshotel.web;

import org.adorsys.geshotel.resto.domain.Inventory;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "inventorys", formBackingObject = Inventory.class)
@RequestMapping("/inventorys")
@Controller
public class InventoryController {
}
