package org.adorsys.geshotel.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.adorsys.geshotel.booking.domain.Deposit;
import org.adorsys.geshotel.domain.Customer;
import org.adorsys.geshotel.domain.security.SecurityUtil;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RooWebScaffold(path = "deposits", formBackingObject = Deposit.class)
@RequestMapping("/deposits")
@Controller
public class DepositController {
	
	 @RequestMapping(method = RequestMethod.POST)
	    public String create(@Valid Deposit deposit, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
	        if (bindingResult.hasErrors()) {
	            uiModel.addAttribute("deposit", deposit);
	            addDateTimeFormatPatterns(uiModel);
	            return "deposits/create";
	        }
	        Customer customer = Customer.findCustomer(deposit.getCustomer().getId());
	        customer.setAccount(customer.getAccount().add(deposit.getDepositvalue()));
	        deposit.setDateDeposit(new Date());
	        deposit.setUserAccount(SecurityUtil.getBaseUser());
	        uiModel.asMap().clear();
	        deposit.persist();
	        return "redirect:/deposits/" + encodeUrlPathSegment(deposit.getId().toString(), httpServletRequest);
	    }
}
