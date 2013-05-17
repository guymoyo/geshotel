package org.adorsys.geshotel.web;

import static java.lang.System.gc;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.adorsys.geshotel.domain.Customer;
import org.adorsys.geshotel.domain.Hotel;
import org.adorsys.geshotel.domain.security.SecurityUtil;
import org.adorsys.geshotel.resto.domain.BarRestauInvoice;
import org.adorsys.geshotel.resto.domain.BrTable;
import org.adorsys.geshotel.resto.domain.CustomerType;
import org.adorsys.geshotel.resto.domain.KitchenOrder;
import org.adorsys.geshotel.resto.domain.KitchenOrderItem;
import org.adorsys.geshotel.resto.domain.Product;
import org.adorsys.geshotel.resto.domain.Sale;
import org.adorsys.geshotel.resto.domain.SaleItem;
import org.adorsys.geshotel.resto.work.beans.CustomerTypeCmd;
import org.adorsys.geshotel.resto.work.beans.HostedCustomerCmd;
import org.adorsys.geshotel.resto.work.beans.SaleItemCommand;
import org.adorsys.geshotel.resto.work.beans.SaleMerger;
import org.adorsys.geshotel.resto.work.beans.SaleProcess;
import org.adorsys.geshotel.resto.work.beans.SaleToEmployeeCmd;
import org.adorsys.geshotel.resto.work.exception.MoreProductSaledThanInStockException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import flexjson.JSONSerializer;

@RequestMapping(value = "/saleprocess")
@Controller
public class SaleProcessController {

	@Resource
	private SaleMerger saleMerger;
	private Sale latestSelectedSale ;
	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public String manageSale() {
		return "saleindex";
	}
	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String createForm(Model uiModel) {
		Sale sale = new Sale();
		List<Hotel> hotels = Hotel.findAllHotels();
		BigDecimal tva = BigDecimal.ZERO;
		if(hotels != null && !hotels.isEmpty()){
			tva = hotels.iterator().next().getTVA();
		}else{
			tva = new BigDecimal(19.5);
		}
		sale.setTax(tva);
		uiModel.addAttribute("sale", sale);
		populateSaleForm(uiModel);
		return "saleprocess/sales/create";
	}
	private void populateSaleForm(Model uiModel) {
		uiModel.addAttribute("customers", Customer.findAllCustomers());
		uiModel.addAttribute("brtables", BrTable.findAllBrTables());
		uiModel.addAttribute("customertypes", Arrays.asList(CustomerType.class.getEnumConstants()));
		addDateTimeFormatPatterns(uiModel);
	}
	@RequestMapping(value="/sales",params="form",method=RequestMethod.GET)
	public String createSaleForm(Model uiModel) {
		return "redirect:/saleprocess?form";
	}
	@RequestMapping(method = RequestMethod.POST)
	public String create(Sale sale, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (StringUtils.isEmpty(sale.getNote()))
			bindingResult.addError(new ObjectError("note",
					"The note may not be null"));
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("sale", sale);
			addDateTimeFormatPatterns(uiModel);
			return "saleprocess/sales/create";
		}
		uiModel.asMap().clear();
		SaleProcess.getInstance().addSale(sale);
		/*return "redirect:/saleprocess/sales/"
				+ encodeUrlPathSegment(sale.getId().toString(),
						httpServletRequest);*/
		return "redirect:/saleprocess";
	}

	@RequestMapping(value = "/sales/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		Sale sale = Sale.findSale(id);
		if(sale.invoiceGenerated()){
			uiModel.addAttribute("barRestauInvoice", sale.findBarRestauInvoice().iterator().next());
		}
		if(SaleProcess.getInstance().getClosedSales().contains(sale)){
			uiModel.addAttribute("message", "Sale Closed");
		}
		uiModel.addAttribute("sale", sale);
		uiModel.addAttribute("saleitems", SaleItem.findSaleItemsBySale(sale)
				.getResultList());
		uiModel.addAttribute("itemId", id);
		uiModel.addAttribute("customerTypeCmd", new CustomerTypeCmd());
		return "saleprocess/sales/show";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addSaleCommand(SaleItemCommand command,BindingResult bindingResult, Model uiModel,HttpServletRequest request) {
		System.out.println("Command : " + command.toString());
		try {
//			SaleProcess.getInstance().addCommand(command);
			SaleProcess.getInstance().addCommandAndGenerateKitchenOrder(command);
		} catch (MoreProductSaledThanInStockException e) {
			e.printStackTrace();
			uiModel.addAttribute("sales", SaleProcess.getInstance().getOpenedSales());
			addDateTimeFormatPatterns(uiModel);
			SaleItemCommand saleItemCommand = new SaleItemCommand();
			uiModel.addAttribute("saleItemCommand", saleItemCommand);
			uiModel.addAttribute("products", findValidProducts());
			uiModel.addAttribute("exception", e);
			bindingResult.addError(new ObjectError("qty",e.toString()));
			return "saleprocess/sales/list";
		} catch (Exception e) {
			uiModel.addAttribute("exception", e);
			populateSaleForm(uiModel);
			e.printStackTrace();
			return "saleprocess/sales/list";
//			e.printStackTrace();
		}
		this.latestSelectedSale = command.getSale();
		return "redirect:/saleprocess";
	}

	@RequestMapping(value = "/{saleKey}/close", method = RequestMethod.GET)
	public String closeSaleProcess(@PathVariable("saleKey") String saleKey,
			Model uiModel, HttpServletRequest request) {
		try {
			SaleProcess.getInstance().closeSale(saleKey,true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return afterSaleClose(saleKey, uiModel, request,false);
	}
	/**
	 * factoring code. This function is called in, both closeSale(saleKey,uiModel,httpServletRequest) and
	 * closeSaleProcessByForm(@PathVariable("saleKey")String saleKey,CustomerTypeCmd customerTypeCmd,Model uiModel,HttpServletRequest request).
	 * @param saleKey
	 * @param uiModel
	 * @param httpServletRequest
	 * @return
	 */
	private String afterSaleClose(String saleKey, Model uiModel,HttpServletRequest httpServletRequest,boolean wasDetails){
		Sale sale = Sale.findSalesBySaleKeyEquals(saleKey).getSingleResult();
		uiModel.addAttribute("customerTypeCmd", new CustomerTypeCmd());
		uiModel.addAttribute("sale", sale);
		uiModel.addAttribute("taxValue", SaleProcess.getInstance().getSaleTotal(sale));
		uiModel.addAttribute("saleitems", SaleItem.findSaleItemsBySale(sale).getResultList());
		uiModel.addAttribute("itemId", sale.getId());
		if(!wasDetails)
		uiModel.addAttribute("message", "Sale closed");
		return "saleprocess/sales/show";
	}
	@RequestMapping(value = "/{saleKey}/close", method = RequestMethod.POST)
	public String closeSaleProcessByForm(@PathVariable("saleKey")String saleKey,CustomerTypeCmd customerTypeCmd,Model uiModel,HttpServletRequest request){
		String customerType = customerTypeCmd.getCustomerType();
		System.out.println("\n \n Customer Type : "+customerType);
		if(CustomerTypeCmd.SIMPLE_CUSTOMER.equals(customerTypeCmd.getCustomerType())){
			//simply close the invoice, since it is a simple customer.
			try {
				SaleProcess.getInstance().closeSale(saleKey,true);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				gc();
			}
			return afterSaleClose(saleKey, uiModel, request,false);
		}else if(CustomerTypeCmd.HOSTED_CUSTOMER.equals(customerTypeCmd.getCustomerType())){
			HostedCustomerCmd hostedCustomerCmd = new HostedCustomerCmd();
			hostedCustomerCmd.setSaleKey(saleKey);
//			uiModel.addAttribute("", attributeValue)
			uiModel.addAttribute("hostedCustomerCmd",  hostedCustomerCmd);
			return "saleprocess/customers/findHostedCustomer";
		}else{
			SaleToEmployeeCmd saleToEmployeeCmd = new SaleToEmployeeCmd();
			saleToEmployeeCmd.setSaleKey(saleKey);
			uiModel.addAttribute("saleToEmployeeCmd", saleToEmployeeCmd);
			return "saleprocess/employees/selectEmployee";
		}
	}
	@RequestMapping(value = "/{saleKey}/showInvoiceDetail", method = RequestMethod.POST)
	public String showSaleDetail(@PathVariable("saleKey")String saleKey,CustomerTypeCmd customerTypeCmd,Model uiModel,HttpServletRequest request){
		String customerType = customerTypeCmd.getCustomerType();
		System.out.println("\n \n Customer Type : "+customerType);
		if(CustomerTypeCmd.SIMPLE_CUSTOMER.equals(customerTypeCmd.getCustomerType())|| CustomerType.HOSTED_CUSTOMER.equals(customerTypeCmd.getCustomerType())){
			//simply close the invoice, since it is a simple customer.
			try {

				System.out.println("\n \n Working on Simple, Hosted customer ");
				SaleProcess.getInstance().closeSale(saleKey,false);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				gc();
			}
			return afterSaleClose(saleKey, uiModel, request,true);
		}else{
			try {
				System.out.println("\n \n Working on employee");
				SaleProcess.getInstance().showSaleDetailsForEmployee(saleKey);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				gc();
			}
			return afterSaleClose(saleKey, uiModel, request,true);
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		/*
		 * if (page != null || size != null) { int sizeNo = size == null ? 10 :
		 * size.intValue(); uiModel.addAttribute("sales",
		 * Sale.findSaleEntries(page == null ? 0 : (page.intValue() - 1) *
		 * sizeNo, sizeNo)); float nrOfPages = (float) Sale.countSales() /
		 * sizeNo; uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int)
		 * nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages)); } else
		 * { uiModel.addAttribute("sales", Sale.findAllSales()); }
		 */
		List<Sale> openedSales = SaleProcess.getInstance().getOpenedSales();
		if(openedSales != null && !openedSales.isEmpty() && this.latestSelectedSale != null){
			openedSales.remove(latestSelectedSale);
			openedSales.add(0, latestSelectedSale);
		}
		uiModel.addAttribute("sales", openedSales);
		addDateTimeFormatPatterns(uiModel);
		SaleItemCommand saleItemCommand = new SaleItemCommand();
		uiModel.addAttribute("saleItemCommand", saleItemCommand);
		uiModel.addAttribute("products", findValidProducts());
		return "saleprocess/sales/list";
	}

	private List<Product> findValidProducts() {
		return Product.findValidProductsByUser(SecurityUtil.getBaseUser(),BigDecimal.ZERO);
	}
	@RequestMapping(value = "/closed", method = RequestMethod.GET)
	public String listClosedSaled(Model uiModel, HttpServletRequest request) {
		uiModel.addAttribute("sales", SaleProcess.getInstance()
				.getClosedSales());
		return "saleprocess/closed";
	}

	@RequestMapping(value = "/{saleKey}/invoice", method = RequestMethod.GET)
	public String generateSaleInvoice(@PathVariable("saleKey") String saleKey,
			Model uiModel, HttpServletRequest request) {
		BarRestauInvoice barRestauInvoice = null;
		try {
			barRestauInvoice = SaleProcess.getInstance().generateInvoice(saleKey);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			gc();
		}
		Assert.notNull(barRestauInvoice, "The Invoice may not be null");
		uiModel.addAttribute("barrestauinvoice", barRestauInvoice);
		return "saleprocess/barrestauinvoices/show";
	}

	@RequestMapping(value = "/merge", params = { "init" }, method = RequestMethod.GET)
	public String mergeSaleInit(HttpServletRequest request, Model uiModel) {
		uiModel.addAttribute("sales", SaleProcess.getInstance().getOpenedSales());
		return "saleprocess/merges/choosebasesale";
	}

	@RequestMapping(value = "/merge/{saleKey}/basesale", method = RequestMethod.GET)
	public String addBaseSale(@PathVariable("saleKey") String saleKey,
			Model uiModel) {
		System.out.println("base sale");
		Set<Sale> sales = null;
		try {
			sales = saleMerger.addOriginalSale(saleKey);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			gc();
		}
		uiModel.addAttribute("saleMerger", saleMerger);
		uiModel.addAttribute("sales", sales);
		return "saleprocess/merges/chooseothersales";
	}

	@RequestMapping(value = "/merge/{saleKey}/add", method = RequestMethod.GET)
	public String addOtherSale(@PathVariable("saleKey") String saleKey,
			Model uiModel) {
		System.out.println("other sale");
		Set<Sale> sales = null;
		try {
			sales = saleMerger.addOtherSale(saleKey);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			gc();
		}
		uiModel.addAttribute("sales", sales);
		uiModel.addAttribute("saleMerger", saleMerger);
		return "saleprocess/merges/chooseothersales";
	}
	@RequestMapping(value = "/merge/{saleKey}/remove", method = RequestMethod.GET)
	public String removeOtherSale(@PathVariable("saleKey") String saleKey,
			HttpServletRequest request, Model uiModel) {
		Set<Sale> sales = null;
		try {
			sales = saleMerger.removeOtherSale(saleKey);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			gc();
		}
		uiModel.addAttribute("sales", sales);
		uiModel.addAttribute("saleMerger", saleMerger);
		return "saleprocess/merges/chooseothersales";
	}

	@RequestMapping(value = "/merge", params = { "mergeall" }, method = RequestMethod.GET)
	public String mergeSales(HttpServletRequest request, Model uiModel) {
		try {
			saleMerger.mergeSales();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			System.gc();
		}
		return "redirect:/saleprocess/sales/"
				+ encodeUrlPathSegment(saleMerger.getOriginalSale().getId()
						.toString(), request);
	}
	@RequestMapping(value="/hostedcustomercmd",method=RequestMethod.POST)
	public String findHostedCustomer(HostedCustomerCmd hostedCustomerCmd,Model uiModel,HttpServletRequest httpServletRequest){
		System.out.println("\n \n hosted customer cmd :    \t "+hostedCustomerCmd.toString());
		if(hostedCustomerCmd.checkHostedCustomer()){
			try {
				SaleProcess.getInstance().closeSale(hostedCustomerCmd.getSaleKey(), hostedCustomerCmd.getCustomer(),true);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				gc();
			}
			uiModel.addAttribute("customerTypeCmd", new CustomerTypeCmd()); 
			return afterSaleClose(hostedCustomerCmd.getSaleKey(), uiModel, httpServletRequest,false);
		}else{
			String error = "Invalid Customer, please call the booking office" ;
			uiModel.addAttribute("hostedCustomerCmd",  hostedCustomerCmd);
			uiModel.addAttribute("error", error);
			return "saleprocess/customers/findHostedCustomer";
		}
	}
	@RequestMapping(value="/saleToEmployee",method=RequestMethod.POST)
	public String saleToEmployee(SaleToEmployeeCmd saleToEmployeeCmd,Model uiModel,HttpServletRequest httpServletRequest){
		System.out.println("\n \n hosted customer cmd :    \t "+saleToEmployeeCmd.toString());
		try {
			SaleProcess.getInstance().closeSale(saleToEmployeeCmd.getSaleKey(), saleToEmployeeCmd.getEmployee(),true);
		} catch (Exception e) {

			System.out.println("\n \n Error : \t"+e.getMessage());
			e.printStackTrace();
		}finally{
			gc();
		}
		return afterSaleClose(saleToEmployeeCmd.getSaleKey(), uiModel, httpServletRequest,false);
	}
	@RequestMapping(value="/findSaleAjax",method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> findSaleAjax(@RequestParam("saleKey")String saleKey,HttpServletRequest request){
		String saleToJson = SaleProcess.getInstance().findSaleToJson(saleKey);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/text; charset=utf-8");
		System.out.println("\n \n [Detailed Sale Bean  ] : "+saleToJson);
		return new ResponseEntity<String>(saleToJson, headers, HttpStatus.OK);
	}
	@RequestMapping(value="/checkKithenOrderAjax",method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> checkKitchenOrder(@RequestParam("saleKey")String saleKey,HttpServletRequest httpServletRequest){
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Type", "application/text; charset=utf-8");
		String result = "";
		try {
			KitchenOrder kitchenOrders = SaleProcess.getInstance().findKitchenOrder(saleKey).iterator().next();
			result = new JSONSerializer().exclude("*.class").serialize(kitchenOrders);
		} catch (Exception e) {
			System.out.println("\n \n Error : \t"+e.getMessage());
		}
		return new ResponseEntity<String>(result, httpHeaders, HttpStatus.OK);
	}
	@RequestMapping(value="/viewKitchenOrder",method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> viewKitchenOrder(@RequestParam("kitchenOrderKey")String kitchenOrderKey,Model uiModel,HttpServletRequest httpServletRequest){
		List<KitchenOrderItem> kitchenOrderItems = null;
		try {
			System.out.println("\n \n Kitchen Order Key "+kitchenOrderKey);
			KitchenOrder kitchenOrder = KitchenOrder.findKitchenOrdersByKitchenOrderKeyEquals(kitchenOrderKey).getSingleResult();
			System.out.println("\n \n Kitchen Order Found \t"+kitchenOrder);
			kitchenOrderItems = KitchenOrderItem.findKitchenOrderItemsByKitchenOrder(kitchenOrder).getResultList();
		} catch (Exception e) {
			System.out.println("\n \n Error : \t"+e.getMessage());
		}
		return prepareHttpEntity(kitchenOrderItems);
	}
	@RequestMapping(value="/removesaleitem",params="saleItemKey",method=RequestMethod.GET)
	public String removeSaleItem(@RequestParam("saleItemKey")String saleItemKey,Model uiModel){
		try {
			SaleProcess.getInstance().removeSaleItem(saleItemKey);
		} catch (Exception e) {
			uiModel.addAttribute("exception", e);
			System.out.println("\n \n Error : \t"+e.getMessage());
		}
		return "redirect:/saleprocess";
	}
	private  ResponseEntity<String> prepareHttpEntity(Object obj){
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Type", "application/text; charset=utf-8");
		String result = new JSONSerializer().exclude("*.class").serialize(obj);
		return new ResponseEntity<String>(result, httpHeaders, HttpStatus.OK);
	}
	@ModelAttribute(value = "saleEntries")
	public Collection<Sale> populateSaleEntries() {
		List<Sale> openedSales = SaleProcess.getInstance().getOpenedSales();
		if(openedSales != null && !openedSales.isEmpty() && this.latestSelectedSale != null){
			System.out.println("Not Null");
			openedSales.remove(latestSelectedSale);
			openedSales.add(0, latestSelectedSale);
		}
		return openedSales;
	}

/*	@ModelAttribute("brtables")
	public Collection<BrTable> populateBrTables() {
		return BrTable.findAllBrTables();
	}
*/
	
	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute(
				"sale_recorddate_date_format",
				DateTimeFormat.patternForStyle("M-",
						LocaleContextHolder.getLocale()));
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


