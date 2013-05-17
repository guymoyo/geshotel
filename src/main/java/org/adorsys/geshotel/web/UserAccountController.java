package org.adorsys.geshotel.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.adorsys.geshotel.domain.UserAccount;
import org.adorsys.geshotel.domain.security.SecurityUtil;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "useraccounts", formBackingObject = UserAccount.class)
@RequestMapping("/useraccounts")
@Controller
public class UserAccountController {
	
	@RequestMapping("/account")
	public String accountDetail(Model uiModel,HttpSession session,HttpServletRequest httpServletRequest) {
		
		UserAccount user = SecurityUtil.getBaseUser();
		
		return "redirect:/useraccounts/" + encodeUrlPathSegment(user.getId().toString(), httpServletRequest);
	}
}
