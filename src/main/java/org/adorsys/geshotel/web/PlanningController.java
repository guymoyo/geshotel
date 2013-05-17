package org.adorsys.geshotel.web;

import java.util.Collection;
import java.util.Date;

import org.adorsys.geshotel.booking.domain.RoomCategory;
import org.adorsys.geshotel.booking.planning.PlanningService;
import org.adorsys.geshotel.utility.DateUtility;
import org.apache.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/planning")
@Controller
public class PlanningController {
	
	private Logger log =Logger.getLogger("planning");
	
	@ModelAttribute("category")
	public Collection<RoomCategory> populateProvinces(){
		return RoomCategory.findAllRoomCategorys();
	}
	

	
	@RequestMapping
	public String plane(@RequestParam(value="from", required=false)@DateTimeFormat(pattern="yyyy-MM-dd") Date from,
    		@RequestParam(value = "category", required = false) Long category,
    		Model uiModel) {
		
		RoomCategory roomCategory = getRoomCategory(category);
		if(from==null){
			from = DateUtility.buildDate(new Date());
		}
		
		PlanningService.getPlanning(from, roomCategory, uiModel);
		return"planning";
	}

	
	@RequestMapping("/previous")
	public String previous(@RequestParam(value="from", required=true)@DateTimeFormat(pattern="yyyy-MM-dd") Date from,
    		@RequestParam(value = "category", required = false) Long category,
    		Model uiModel) {
		
		RoomCategory roomCategory = getRoomCategory(category);
		from = DateUtility.addDayToDate(from, -7);
		PlanningService.getPlanning(from, roomCategory, uiModel);
		return"planning";
	}
	
	@RequestMapping("/next")
	public String next(@RequestParam(value="from", required=true)@DateTimeFormat(pattern="yyyy-MM-dd") Date from,
    		@RequestParam(value = "category", required = false) Long category,
    		Model uiModel) {
		
		RoomCategory roomCategory = getRoomCategory(category);
		from = DateUtility.addDayToDate(from, 7);
		PlanningService.getPlanning(from, roomCategory, uiModel);
		return"planning";
	}
	
	private RoomCategory getRoomCategory(Long category) {
		RoomCategory roomCategory ;
		try {
			  roomCategory = RoomCategory.findRoomCategory(category);
		
		} catch (Exception e) {
		
			roomCategory=null;
			log.warn(category);
		}
		return roomCategory;
	}
	
}
