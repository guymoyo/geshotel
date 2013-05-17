package org.adorsys.geshotel.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.adorsys.geshotel.domain.Hotel;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "hotels", formBackingObject = Hotel.class)
@RequestMapping("/hotels")
@Controller
public class HotelController {
	
	@RequestMapping("/configuration")
	 public String configure( Model uiModel, HttpServletRequest request,HttpSession  session){
		List<Hotel> hotels = Hotel.findAllHotels();
		if(hotels.isEmpty()){
			 uiModel.addAttribute("hotel", new Hotel());
		        return "hotels/create";
		}else{
			 uiModel.addAttribute("hotel", hotels.get(0));
		        return "hotels/update";
		}
		 
	 }
}
