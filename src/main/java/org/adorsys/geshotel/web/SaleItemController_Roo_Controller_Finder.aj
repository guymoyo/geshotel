// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.web;

import java.lang.String;
import org.adorsys.geshotel.resto.domain.Sale;
import org.adorsys.geshotel.resto.domain.SaleItem;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

privileged aspect SaleItemController_Roo_Controller_Finder {
    
    @RequestMapping(params = { "find=BySale", "form" }, method = RequestMethod.GET)
    public String SaleItemController.findSaleItemsBySaleForm(Model uiModel) {
        uiModel.addAttribute("sales", Sale.findAllSales());
        return "saleitems/findSaleItemsBySale";
    }
    
    @RequestMapping(params = "find=BySale", method = RequestMethod.GET)
    public String SaleItemController.findSaleItemsBySale(@RequestParam("sale") Sale sale, Model uiModel) {
        uiModel.addAttribute("saleitems", SaleItem.findSaleItemsBySale(sale).getResultList());
        return "saleitems/list";
    }
    
    @RequestMapping(params = { "find=BySaleItemKeyEquals", "form" }, method = RequestMethod.GET)
    public String SaleItemController.findSaleItemsBySaleItemKeyEqualsForm(Model uiModel) {
        return "saleitems/findSaleItemsBySaleItemKeyEquals";
    }
    
    @RequestMapping(params = "find=BySaleItemKeyEquals", method = RequestMethod.GET)
    public String SaleItemController.findSaleItemsBySaleItemKeyEquals(@RequestParam("saleItemKey") String saleItemKey, Model uiModel) {
        uiModel.addAttribute("saleitems", SaleItem.findSaleItemsBySaleItemKeyEquals(saleItemKey).getResultList());
        return "saleitems/list";
    }
    
}
