package org.adorsys.geshotel.web.ext;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.adorsys.geshotel.domain.RoleName;
import org.adorsys.geshotel.domain.UserAccount;
import org.adorsys.geshotel.domain.security.SecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/accessdenied")
public class AccessDeniedController {
	/**
	 * set the link log either to the restaurant, or to the reservation
	 * @param request
	 * @param response
	 * @param uiModel
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String accessDenied(HttpServletRequest request,HttpServletResponse response,Model uiModel) {
		UserAccount baseUser = SecurityUtil.getBaseUser();
		Set<RoleName> roleNames = baseUser.getRoleNames();
		uiModel.addAttribute("user", request.getUserPrincipal().getName());
//		uiModel.addAttribute("link", "")
		return "accessdenied";
	}
}
