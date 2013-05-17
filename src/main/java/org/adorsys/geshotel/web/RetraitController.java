package org.adorsys.geshotel.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.adorsys.geshotel.booking.domain.Retrait;
import org.adorsys.geshotel.domain.Customer;
import org.adorsys.geshotel.domain.security.SecurityUtil;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RooWebScaffold(path = "retraits", formBackingObject = Retrait.class)
@RequestMapping("/retraits")
@Controller
public class RetraitController {
	
	 @RequestMapping(method = RequestMethod.POST)
	    public String create(@Valid Retrait retrait, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
	        if (bindingResult.hasErrors()) {
	            uiModel.addAttribute("retrait", retrait);
	            addDateTimeFormatPatterns(uiModel);
	            return "retraits/create";
	        }
	        Customer customer = Customer.findCustomer(retrait.getCustomer().getId());
	        customer.setAccount(customer.getAccount().add(retrait.getRetraitvalue().negate()));
	        retrait.setDateRetrait(new Date());
	        retrait.setUserAccount(SecurityUtil.getBaseUser());
	        uiModel.asMap().clear();
	        retrait.persist();
	        return "redirect:/retraits/" + encodeUrlPathSegment(retrait.getId().toString(), httpServletRequest);
	    }
}
