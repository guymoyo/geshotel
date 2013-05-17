package org.adorsys.geshotel.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.adorsys.geshotel.booking.domain.LabelChart;
import org.adorsys.geshotel.booking.domain.Reservation;
import org.adorsys.geshotel.booking.domain.Room;
import org.adorsys.geshotel.domain.Payment;
import org.adorsys.geshotel.utility.DateUtility;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/stats")
@Controller
public class StatistiqueController {
	
	@RequestMapping("/venteForm")
	public String venteForm(Model uiModel, HttpServletRequest httpServletRequest){
		
		return "stats/venteForm";
	}
	
	
	@RequestMapping("/vente")
	public String vente(Model uiModel, HttpServletRequest httpServletRequest
			, @RequestParam("minDay")@DateTimeFormat(pattern="yyyy-MM-dd") Date minDay
			, @RequestParam("maxDay")@DateTimeFormat(pattern="yyyy-MM-dd") Date maxDay) {
		
		int numDays =  DateUtility.diff(maxDay, minDay);

		ArrayList<Long> list = new ArrayList<Long>();
		ArrayList<String> list2 = new ArrayList<String>();
		
		for(int i=0;i<=numDays;i++){
			Date toDate = DateUtility.addDayToDate(minDay, i);
			list.add(cashToday(toDate));
			SimpleDateFormat formatter = new SimpleDateFormat("E dd MMM");
			String s = formatter.format(toDate);
			list2.add(new LabelChart(s, i+1).toJson());
		}
		
		JSONArray jsonObject = JSONArray.fromObject( list );
		
		uiModel.addAttribute("map", jsonObject);
		uiModel.addAttribute("list",list2 );
		return "stats/vente";
	}
	
	@RequestMapping("/overviewForm")
	public String overviewForm(Model uiModel, HttpServletRequest httpServletRequest){
		
		return "stats/statForm";
	}
	
	@RequestMapping("/overview")
	public String overview(Model uiModel, HttpServletRequest httpServletRequest
			, @RequestParam("minDay")@DateTimeFormat(pattern="yyyy-MM-dd") Date day) {
		
		
		tauxOccupation(uiModel, day);
		uiModel.addAttribute("indiceFrequentation", indiceFrequentation(day));
		uiModel.addAttribute("revenuMoyenChambre", revenuMoyenChambre(day));
		
		return "stats/stats";
	}
	
	void tauxOccupation(Model uiModel, Date day){
		
		List<Reservation> list = Reservation.getReservationOccuped(day);
		
		int roomOccuped;
		try {
			 roomOccuped = list.size();
		} catch (Exception e) {
			 roomOccuped=0;
		}
		
		int size;
		try {
			 size = Room.findAllRooms().size();
		} catch (Exception e) {
			 size = 0;
		}
		
		ArrayList<Long> listX = new ArrayList<Long>();
		listX.add((long)roomOccuped);
		listX.add((long)size);
		uiModel.addAttribute("listX", JSONArray.fromObject(listX));
		
		ArrayList<String> listY = new ArrayList<String>();
		listY.add(new LabelChart("Chambre Occupe", 1).toJson());
		listY.add(new LabelChart("Chambre Disponible", 2).toJson());
		uiModel.addAttribute("listY", JSONArray.fromObject(listY));
		
		uiModel.addAttribute("to", (float)roomOccuped/(float)size);
	}
	
	float indiceFrequentation(Date day){
		
		List<Reservation> list = Reservation.getReservationOccuped(day);
		if (list == null || list.isEmpty())
			return 0;
		
		float numPers = 0;
		float numReser = list.size();
		
		for(Reservation r:list){
			numPers += r.getNumOfPeople();
		}
		
		
		return numPers/numReser;
	}
	
	float revenuMoyenChambre(Date day){
		
		List<Reservation> list = Reservation.getReservationOccuped(day);
		if (list == null || list.isEmpty())
			return 0;
		
		float ca = 0;
		float numReser = list.size();
		
		for(Reservation r:list){
			ca += r.getAmount().floatValue();
		}
		
		
		return ca/numReser;
	}
	
	
	Long cashToday(Date day){
		
		Date date = day;
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		Date date2 = DateUtility.addDayToDate(day, 1);
		date2.setHours(0);
		date2.setMinutes(59);
		date2.setSeconds(59);
		
		List<Payment> listp = Payment.findPaymentsByDatePayemmentBetween(
				date, date2).getResultList();
		BigDecimal montant = BigDecimal.ZERO;
		for (Payment p : listp) {
			montant = montant.add(p.getAmount());
		}
		
		return montant.longValue();
	}

}
