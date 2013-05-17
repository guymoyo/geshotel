package org.adorsys.geshotel.web;

import org.adorsys.geshotel.resto.domain.Discount;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "discounts", formBackingObject = Discount.class)
@RequestMapping("/discounts")
@Controller
public class DiscountController {
}
