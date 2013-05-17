package org.adorsys.geshotel.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.adorsys.geshotel.resto.domain.BarRestauState;
import org.adorsys.geshotel.resto.domain.KitchenOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/kitchenprocess")
public class KitchenController {
	@RequestMapping(value="/currentorders",method=RequestMethod.GET)
	public String listCurrentOrders(Model uiModel,HttpServletRequest httpServletRequest){
		List<KitchenOrder> kitchenOrders = KitchenOrder.findKitchenOrdersByKitchenOrderItemState(BarRestauState.OPENED).getResultList();
		uiModel.addAttribute("kitchenorders", kitchenOrders);
		return "kitchenorders/list";
	}
}
