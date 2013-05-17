package org.adorsys.geshotel.web.ext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.adorsys.geshotel.domain.security.SecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/notallowed")
public class NotAllowedToWork {
	
	@RequestMapping(method=RequestMethod.GET)
	public String applicationIsClosed(HttpServletRequest request,HttpServletResponse response,Model uiModel){
		uiModel.addAttribute("user", SecurityUtil.getBaseUser());
		return "applicationIsClosed";
	}
}
