// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.web;

import java.lang.String;
import org.adorsys.geshotel.resto.domain.KitchenOrder;
import org.adorsys.geshotel.resto.domain.KitchenOrderItem;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

privileged aspect KitchenOrderItemController_Roo_Controller_Finder {
    
    @RequestMapping(params = { "find=ByKitchenOrder", "form" }, method = RequestMethod.GET)
    public String KitchenOrderItemController.findKitchenOrderItemsByKitchenOrderForm(Model uiModel) {
        uiModel.addAttribute("kitchenorders", KitchenOrder.findAllKitchenOrders());
        return "kitchenorderitems/findKitchenOrderItemsByKitchenOrder";
    }
    
    @RequestMapping(params = "find=ByKitchenOrder", method = RequestMethod.GET)
    public String KitchenOrderItemController.findKitchenOrderItemsByKitchenOrder(@RequestParam("kitchenOrder") KitchenOrder kitchenOrder, Model uiModel) {
        uiModel.addAttribute("kitchenorderitems", KitchenOrderItem.findKitchenOrderItemsByKitchenOrder(kitchenOrder).getResultList());
        return "kitchenorderitems/list";
    }
    
}