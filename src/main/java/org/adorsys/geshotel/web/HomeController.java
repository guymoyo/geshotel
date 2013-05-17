package org.adorsys.geshotel.web;

import java.util.Collection;

import org.adorsys.geshotel.booking.domain.RoomCategory;
import org.adorsys.geshotel.domain.DataInitializerHelper;
import org.adorsys.geshotel.domain.UserAccount;
import org.adorsys.geshotel.domain.security.SecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@RequestMapping("/")
@Controller
public class HomeController {
	
	@ModelAttribute("category")
	public Collection<RoomCategory> populateProvinces(){
		return RoomCategory.findAllRoomCategorys();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String home(){
		UserAccount baseUser = SecurityUtil.getBaseUser();
		if(baseUser == null) return "index";
		if(baseUser.hasRole(DataInitializerHelper.getBarRestoRolesName())){
			return "redirect:/resto";
		}else if(baseUser.hasRole(DataInitializerHelper.getBookingRolesName())){
			return "redirect:/booking";
		}else if(baseUser.hasRole(DataInitializerHelper.getAdminRoleNames())){
			return "admin";
		}
		return "index";
	}
	
	@RequestMapping("/booking")
	public String selectPage() {
		return"redirect:/reservations/mainCourante";
	}
	                                             
	@RequestMapping("/resto")
	public String select() {
		
		return"indexResto";
	}
	@RequestMapping("/backtotop")
	public String backToTop(){
		return "indexResto";
	}
	
}
