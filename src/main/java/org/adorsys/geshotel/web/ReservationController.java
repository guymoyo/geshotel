package org.adorsys.geshotel.web;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.sf.json.JSONArray;

import org.adorsys.geshotel.booking.domain.Deposit;
import org.adorsys.geshotel.booking.domain.Invoice;
import org.adorsys.geshotel.booking.domain.InvoiceState;
import org.adorsys.geshotel.booking.domain.LabelChart;
import org.adorsys.geshotel.booking.domain.PayementState;
import org.adorsys.geshotel.booking.domain.Periode;
import org.adorsys.geshotel.booking.domain.Reservation;
import org.adorsys.geshotel.booking.domain.ReservationState;
import org.adorsys.geshotel.booking.domain.Room;
import org.adorsys.geshotel.domain.Customer;
import org.adorsys.geshotel.domain.Gender;
import org.adorsys.geshotel.domain.Hotel;
import org.adorsys.geshotel.domain.security.SecurityUtil;
import org.adorsys.geshotel.resto.domain.ProductGroup;
import org.adorsys.geshotel.resto.domain.Sale;
import org.adorsys.geshotel.resto.work.beans.AllGroupAndSaleReport;
import org.adorsys.geshotel.resto.work.beans.CustomReservationImg;
import org.adorsys.geshotel.resto.work.beans.GroupAndSaleReport;
import org.adorsys.geshotel.resto.work.beans.SaleProcess;
import org.adorsys.geshotel.utility.DateUtility;
import org.adorsys.geshotel.utility.ErrorMessage;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RooWebScaffold(path = "reservations", formBackingObject = Reservation.class)
@RequestMapping("/reservations")
@Controller
public class ReservationController {

	@RequestMapping("/new")
	public String newResevation(
			@RequestParam(value = "whichDay", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date whichDay,
			@RequestParam(value = "roomId", required = false) Long roomId,
			Model uiModel, HttpSession session) {

		session.setAttribute("roomId", roomId);
		session.setAttribute("whichDay", whichDay);
		uiModel.addAttribute("customer", new Customer());
		uiModel.addAttribute("customers", Customer.findAllCustomers());
		addDateTimeFormat(uiModel);
		return "reservations/customer";
	}

	@RequestMapping("/create")
	public String createCustomer(@Valid Customer customer,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("customer", customer);
			uiModel.addAttribute("customers", Customer.findAllCustomers());
			addDateTimeFormat(uiModel);
			return "reservations/customer";
		}
		uiModel.asMap().clear();
		customer.persist();
		return "redirect:/reservations/stepOne?customers=" + customer.getId();
	}

	@Transactional
	@RequestMapping("/stepOne")
	public String previous(@RequestParam(value = "customers") Long customerId,
			Model uiModel, HttpSession session) {

		if (session.getAttribute("roomId") == null
				|| session.getAttribute("whichDay") == null) {
			return "redirect:/planning";
		}
		Long roomId = (Long) session.getAttribute("roomId");
		Date whichDay = (Date) session.getAttribute("whichDay");
		Room room = Room.findRoom(roomId);
		Customer customer = Customer.findCustomer(customerId);
		List<Reservation> list = Reservation.findReservationsByCustomer(
				customer).getResultList();

		Reservation reservation = new Reservation();
		reservation.setCheckInDate(whichDay);
		reservation.setCheckOutDate(whichDay);
		reservation.setRoom(room);
		reservation.setCustomer(customer);
		reservation.setNumDays(1);

		try {
			uiModel.addAttribute("list", list.get(0));
		} catch (Exception e) {

		}
		uiModel.addAttribute("room", room);
		uiModel.addAttribute("customer", customer);
		uiModel.addAttribute("reservation", reservation);
		addDateTimeFormatPatterns(uiModel);
		return "reservations/create";
	}

	@Transactional
	@RequestMapping(method = RequestMethod.POST)
	public String create(Reservation reservation, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {

		uiModel.asMap().clear();
		Customer customer = Customer.findCustomer(reservation.getCustomer()
				.getId());
		Room room = Room.findRoom(reservation.getRoom().getId());
		reservation.setCustomer(customer);
		reservation.setRoom(room);
		reservation.setAmount(room.getRoomCategory().getPrice());
		reservation.setReservationState(ReservationState.NONCONFIRME);
		reservation.setCreationDate(new Date());

		if (!reservation.verif(uiModel)) {
			uiModel.addAttribute("room", room);
			uiModel.addAttribute("customer", customer);
			uiModel.addAttribute("reservation", reservation);
			addDateTimeFormatPatterns(uiModel);
			return "reservations/create";
		}
		reservation.persist();
		return "redirect:/reservations/"
				+ encodeUrlPathSegment(reservation.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping("/detail")
	public String detail(
			@RequestParam(value = "reservationId", required = false) Long id,
			Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("reservation", Reservation.findReservation(id));
		uiModel.addAttribute("itemId", id);
		return "reservations/show";
	}
	@Transactional
	@RequestMapping("/supprimer")
	public String supprimer(
			@RequestParam(value = "reservationId", required = false) Long id,
			Model uiModel) {
		Reservation reservation = Reservation.findReservation(id);
		if (reservation.isNonConfirmed()) {

			reservation.remove();
		}

		return "redirect:/planning";
	}
	
	@Transactional
	@RequestMapping("/modifier")
	public String modifier(
			@RequestParam(value = "reservationId", required = false) Long id,
			Model uiModel) {

		Reservation reservation = Reservation.findReservation(id);
		if (reservation.isNonConfirmed()) {
			Reservation reservation2 = new Reservation(
					reservation.getCheckInDate(),
					reservation.getCheckOutDate(), reservation.getRoom(),
					reservation.getRoom().getRoomCategory().getPrice(),
					reservation.getReduction(), reservation.getComplement(),
					reservation.getCustomer(), reservation.getReceipt(),
					reservation.getReservationState(),
					reservation.getOtherValue());

			reservation2.setNumDays(reservation.getNumDays());
			reservation.remove();
			uiModel.addAttribute("room", reservation2.getRoom());
			uiModel.addAttribute("customer", reservation2.getCustomer());
			uiModel.addAttribute("reservation", reservation2);
			addDateTimeFormatPatterns(uiModel);
			return "reservations/create";
		}
		return "redirect:/planning";
	}

	@RequestMapping("/imprimer")
	public String imprimer(
			@RequestParam(value = "reservationId", required = false) Long id,
			Model uiModel) {

		return "";
	}

	@Transactional
	@RequestMapping("/confirme")
	public String confirme(
			@RequestParam(value = "reservationId", required = false) Long id,
			@RequestParam(value = "deposit", required = false) BigDecimal deposit,
			Model uiModel) {
		Reservation reservation = Reservation.findReservation(id);
		if (reservation.isNonConfirmed()) {
			
			reservation.setReservationState(ReservationState.CONFIRME);
			reservation.setDeposit(deposit);
			reservation.merge();
			reservation.flush();
			new Deposit("caution of Reservation", deposit, new Date(), SecurityUtil.getBaseUser(), reservation.getCustomer()).persist();
		}
		return "redirect:/planning";
	}

	@RequestMapping("/occupe")
	public String cloturer(@RequestParam(value = "reservationId", required = false) Long id, Model uiModel) {
		Reservation reservation = Reservation.findReservation(id);
		if (reservation.getReservationState()!=ReservationState.OCCUPE) {
			Invoice invoice = new Invoice(reservation.getCustomer(), PayementState.NotPay, InvoiceState.Ouvert, SecurityUtil.getBaseUser(), new Date());
			invoice.persist();
			invoice.flush();
			reservation.setInvoice(invoice);
		}
		reservation.setReservationState(ReservationState.OCCUPE);
		reservation.merge();
		reservation.flush();

		return "redirect:/planning";
	}
	
	@RequestMapping("/libere")
	public String libere(
			@RequestParam(value = "reservationId", required = false) Long id,
			Model uiModel) {
		Reservation reservation = Reservation.findReservation(id);
		
		
		reservation.setReservationState(ReservationState.LIBERE);
		reservation.merge();
		reservation.flush();

		return "redirect:/planning";
	}


	@RequestMapping("/listing")
	public String listRcustomer(
			@RequestParam(value = "customer") Long customerId, Model uiModel,
			HttpSession session) {

		Customer customer = Customer.findCustomer(customerId);
		List<Reservation> list = Reservation.findReservationsByCustomer(
				customer).getResultList();
		uiModel.addAttribute("reservations", list);
		return "reservations/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/calculReduction")
	public @ResponseBody
	String calculReduction(@RequestParam(value = "roomId") String room,
			@RequestParam(value = "remise") String discount) {

		try {
			Long roomId = Long.parseLong(room);
			Integer disc = Integer.parseInt(discount);
			Room findRoom = Room.findRoom(roomId);
			BigDecimal price = findRoom.getRoomCategory().getPrice();
			return price.multiply(new BigDecimal(disc))
					.divide(new BigDecimal(100)).toString();

		} catch (NumberFormatException e) {
			return "0";
		}

	}

	@RequestMapping("/changeDate")
	public String changeDate(Reservation r, Model uiModel) {

		Reservation reservation = Reservation.findReservation(r.getId());
		
		// verifier sil nya pas collision avec une autre reservation en ajoutant
		// le nbre de jour
		if(r.getNumDays()==reservation.getNumDays()){
			addDateTimeFormatPatterns(uiModel);
			uiModel.addAttribute("reservation", reservation);
			uiModel.addAttribute("itemId", reservation.getId());
			return "reservations/show";
		}
		
		
		if(r.getNumDays()>reservation.getNumDays()){
			if(!Reservation.verifCollision(DateUtility.addDayToDate(reservation.getCheckOutDate(), 1), r.getNumDays()-reservation.getNumDays(), uiModel, reservation.getRoom())){
				addDateTimeFormatPatterns(uiModel);
				uiModel.addAttribute("reservation", reservation);
				uiModel.addAttribute("itemId", reservation.getId());
				return "reservations/show";
			}
		}
		Invoice invoice = reservation.getInvoice();
		if(invoice.getInvoiceState()==InvoiceState.Ferme){
			addDateTimeFormatPatterns(uiModel);
			uiModel.addAttribute("reservation", reservation);
			uiModel.addAttribute("itemId", reservation.getId());
			uiModel.addAttribute(
					"error3",
					new ErrorMessage(
							"La facture de cette reservation est close",
							"Invoice of this booking is closed").getMessage());
			return "reservations/show";
		}

		// verifier si le paiyment nes pas superieur au montantde la facture
		int numDays = reservation.getNumDays();

		reservation.setNumDays(r.getNumDays());
		reservation.setCheckOutDate(DateUtility.addDayToDate(
				reservation.getCheckInDate(), reservation.getNumDays() - 1));
		reservation.merge();
		reservation.flush();

		
		reservation.calculTotalAmount();
		invoice.postLoadFunction();
		
		if (invoice.getAmountPaid().compareTo(invoice.getAmountDue()) == 0) {
			invoice.setPayementState(PayementState.Pay);
			invoice.merge();
			invoice.flush();
		} 
								
		if (invoice.getAmountPaid().compareTo(invoice.getAmountDue()) == 1) {

			reservation.setNumDays(numDays);
			reservation
					.setCheckOutDate(DateUtility.addDayToDate(
							reservation.getCheckInDate(),
							reservation.getNumDays() - 1));
			reservation.merge();
			reservation.flush();
			
				uiModel.addAttribute(
						"error3",
						new ErrorMessage(
								"reservation payee,vous ne pouvez dimunue le nombre de jour",
								"booking pay,you can not decrease the number of day").getMessage());
		}
		reservation.calculTotalAmount();
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("reservation", reservation);
		uiModel.addAttribute("itemId", reservation.getId());
		return "reservations/show";
	}
	
	
	 @RequestMapping(method = RequestMethod.GET, value = "ByCustomerAjax")
	    @ResponseBody
	    public String jsonFindReservationsByCustomer(@RequestParam("customer") Customer customer) {
		 List<Reservation> list = Reservation.findReservationsByCustomer(customer).getResultList();
	        return Reservation.toJsonArray(list);
	    }
	 
	 
	 @RequestMapping("/mainCourante")
	 public String mainCourante(Model uiModel, HttpServletRequest httpServletRequest){
		  
		 List<Reservation> reservations = Reservation.reservationWithInvoiceClose();
		List<CustomReservationImg> customReservationImgs = this.transformReservations(reservations);
		for (CustomReservationImg reservationImg : customReservationImgs) {
			SaleProcess.findServicesReports(reservationImg.getCustomer(), InvoiceState.Ouvert,reservationImg);
//			System.out.println("\n Group And sale Reports : \t "+reservationImg.getGroupAndSaleReports());
		}
		 Date day = new Date();
		 tauxOccupation(uiModel, day );
//		 uiModel.addAttribute("reservations", reservations);
			uiModel.addAttribute("indiceFrequentation", indiceFrequentation(day));
			uiModel.addAttribute("revenuMoyenChambre", revenuMoyenChambre(day));
			System.out.println("\n \n custom reservation img : \t "+customReservationImgs);
			List<ProductGroup> allProductGroups = ProductGroup.findAllProductGroups();
			System.out.println("\n \n Services : \t "+allProductGroups);
			uiModel.addAttribute("hotelServices", allProductGroups);
			uiModel.addAttribute("reservations", customReservationImgs);
		 return "reservations/mainCourante";
	 }

	public CustomReservationImg transformReservation(Reservation reservation){
		CustomReservationImg reservationImg = new CustomReservationImg();
		reservationImg.setAmount(reservation.getAmount());
		reservationImg.setCheckInDate(reservation.getCheckInDate());
		reservationImg.setCheckOutDate(reservation.getCheckOutDate());
		reservationImg.setComplement(reservation.getComplement());
		reservationImg.setCreationDate(reservation.getCreationDate());
		reservationImg.setCustomer(reservation.getCustomer());
		reservationImg.setDeposit(reservation.getDeposit());
		reservationImg.setDiscount(reservation.getDiscount());
		reservationImg.setGroupAndSaleReportAmount(BigDecimal.ZERO);
		reservationImg.setGroupAndSaleReports(new ArrayList<GroupAndSaleReport>());
		reservationImg.setId(reservation.getId());
		reservationImg.setInvoice(reservation.getInvoice());
		reservationImg.setNumDays(reservation.getNumDays());
		reservationImg.setNumOfPeople(reservation.getNumOfPeople());
		reservationImg.setOtherValue(reservation.getOtherValue());
		reservationImg.setReceipt(reservation.getReceipt());
		reservationImg.setReduction(reservation.getReduction());
		reservationImg.setReservationState(reservation.getReservationState());
		reservationImg.setRoom(reservation.getRoom());
		reservationImg.setTotalAmount(reservation.getTotalAmount());
		return reservationImg;
	}
	public List<CustomReservationImg> transformReservations(List<Reservation> reservations) {
		List<CustomReservationImg> reservationsImgs = new ArrayList<CustomReservationImg>();
		for (Reservation reservation : reservations) {
			reservationsImgs.add(this.transformReservation(reservation));
		}
		return reservationsImgs;
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
		
	
		// Formulaire d'edition de la fiche police
		@RequestMapping(value="/fichePolice", params={"form"}, method=RequestMethod.GET)
		public String fichePoliceForm(Model uiModel, HttpServletRequest httpServletRequest){
			uiModel.addAttribute("mincreationdate_date_format", "dd-MM-yyyy");
			uiModel.addAttribute("maxcreationdate_date_format", "dd-MM-yyyy");
			uiModel.addAttribute("periode", new Periode());
			return "fichePolice";
		}
		
	 
	void addDateTimeFormat(Model uiModel) {
		uiModel.addAttribute("customer_borndate_date_format", "dd-MM-yyyy");
		uiModel.addAttribute("customer_delivreddate_date_format", "dd-MM-yyyy");
	}

	@ModelAttribute("genders")
	public Collection<Gender> populateGenders() {
		return Arrays.asList(Gender.class.getEnumConstants());
	}

	@ModelAttribute("reservationstates")
	public Collection<ReservationState> populateReservationStates() {
		return Arrays.asList(ReservationState.class.getEnumConstants());
	}
	
	@ModelAttribute("hotel")
	public String nameHotel() {
		String name;
		try {
			name = Hotel.findAllHotels().iterator().next().getName();
		} catch (Exception e) {
			name = "";
		}
		return name;
	}

	String encodeUrlPathSegment(String pathSegment,
			HttpServletRequest httpServletRequest) {
		String enc = httpServletRequest.getCharacterEncoding();
		if (enc == null) {
			enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
		}
		try {
			pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
		} catch (UnsupportedEncodingException uee) {
		}
		return pathSegment;
	}
}
