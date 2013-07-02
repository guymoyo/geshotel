package org.adorsys.geshotel.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.adorsys.geshotel.domain.Customer;
import org.adorsys.geshotel.utility.ErrorMessage;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RooWebScaffold(path = "customers", formBackingObject = Customer.class)
@RequestMapping("/customers")
@Controller
public class CustomerController {
	
	@RequestMapping(method = RequestMethod.GET, value = "/getCustomerByAjax")
	public @ResponseBody
	String getCustomerByAjax(@RequestParam(value = "name") String name) {
		
		List<Customer> list = Customer.findCustomersByLastNameLikeByOrder(name).getResultList();
		List<Customer> list2 = Customer.findCustomersByFirstNameLikeByOrder(name).getResultList();
		
		if(list==null || list.isEmpty())
			return Customer.toJsonArray(list2);
		else
			return Customer.toJsonArray(list);		

	}
	
	 @RequestMapping(method = RequestMethod.POST)
	    public String create(@Valid Customer customer, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
	        if (bindingResult.hasErrors() && customer.isCreated()) {
	            uiModel.addAttribute("customer", customer);
	            uiModel.addAttribute("error", new ErrorMessage("Client deja cree avec ce numero", "Customer ready created with this number").getMessage());
	            addDateTimeFormatPatterns(uiModel);
	            return "customers/create";
	        }
	        uiModel.asMap().clear();
	        customer.persist();
	        return "redirect:/customers/" + encodeUrlPathSegment(customer.getId().toString(), httpServletRequest);
	    }
	 void addDateTimeFormatPatterns(Model uiModel) {
	        uiModel.addAttribute("customer_borndate_date_format", "dd-MM-yyyy");
	        uiModel.addAttribute("customer_delivreddate_date_format", "dd-MM-yyyy");
	    }
}
