package org.adorsys.geshotel.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value="/saleprocess/inventory")
@Controller
public class SaleProcessInventoryController {
	
	@RequestMapping(method=RequestMethod.GET)
	public String list(){
		return "saleprocess/inventory/list";
	}
}
