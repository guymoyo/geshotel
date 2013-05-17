package org.adorsys.geshotel.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.adorsys.geshotel.domain.Customer;
import org.adorsys.geshotel.domain.ext.IdGenerator;
import org.adorsys.geshotel.resto.domain.Procurement;
import org.adorsys.geshotel.resto.domain.ProcurementItem;
import org.adorsys.geshotel.resto.domain.Product;
import org.adorsys.geshotel.resto.domain.Supplier;
import org.adorsys.geshotel.resto.work.beans.ProcurementProcess;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@Controller
@RequestMapping("/procurementprocess")
public class ProcurementProcessController {
	@RequestMapping(params="init")
	public String initProcurement(Model uiModel,HttpServletRequest httpServletRequest, Object Exception){
		Procurement procurement= null;
		try {
			procurement = ProcurementProcess.getInstance().initProcurementProcess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		populateProcurementItemModel(procurement,uiModel, httpServletRequest);
		return "procurementprocess/procurementitems/create";
	}
	@RequestMapping(value="/procurementitems",method = RequestMethod.POST)
    public String create(@Valid ProcurementItem procurementItem, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("procurementItem", procurementItem);
            addDateTimeFormatPatterns(uiModel);
            populateProcurementItemModel(procurementItem.getProcurement(),uiModel, httpServletRequest);
            return "procurementprocess/procurementitems/create";
        }
        procurementItem.setAmount(procurementItem.getQty().multiply(procurementItem.getUnitPrice()));
        procurementItem.setProcurementItemKey(IdGenerator.generateId());
        procurementItem.setDateOfProcurement(new Date());
        uiModel.asMap().clear();
        try {
			ProcurementProcess.getInstance().addProcurementItem(procurementItem,procurementItem.getProcurement());
		} catch (Exception e) {
			e.printStackTrace();
			uiModel.addAttribute("procurementError", e.getMessage());
            uiModel.addAttribute("procurementItem", procurementItem);
            addDateTimeFormatPatterns(uiModel);
            populateProcurementItemModel(procurementItem.getProcurement(),uiModel, httpServletRequest);
            uiModel.addAttribute("exception", e);
            return "procurementprocess/procurementitems/create";
		}
//        procurementItem.persist();
//        return "redirect:/procurementprocess/procurementitems/" + encodeUrlPathSegment(procurementItem.getId().toString(), httpServletRequest);
        populateProcurementItemModel(procurementItem.getProcurement(),uiModel, httpServletRequest);
        return "procurementprocess/procurementitems/create";
    }
    @RequestMapping(value = "/procurementitems/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("procurementitem", ProcurementItem.findProcurementItem(id));
        uiModel.addAttribute("itemId", id);
        return "procurementprocess/procurementitems/show";
    }
    
    @RequestMapping(value="/procurementitems",method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("procurementitems", ProcurementItem.findProcurementItemEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) ProcurementItem.countProcurementItems() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("procurementitems", ProcurementItem.findAllProcurementItems());
        }
        addDateTimeFormatPatterns(uiModel);
        return "procurementprocess/procurementitems/list";
    }
    @RequestMapping(value="/procurements",method = RequestMethod.GET)
    public String listProcurements(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        return list(page, size, uiModel);
    }
    @RequestMapping(value="/procurements",params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
    	return "redirect:/procurementprocess?init";
    }
	@RequestMapping(value="/findLastProcurementItemAjax",method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> findLastProcurementItemAjax(@RequestParam("productId") Long productId, Model uiModel,HttpServletRequest httpServletRequest){
		String previousProcurementItemToJson="";
		try {
			previousProcurementItemToJson = ProcurementProcess.getInstance().findPreviousProcurementItem(Product.findProduct(productId)).toJson();
		} catch (NullPointerException e) {
			previousProcurementItemToJson = "";
		} catch (Exception e) {
			previousProcurementItemToJson = "";
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/text; charset=utf-8");
//		System.out.println("Procuremnt Item Result : "+previousProcurementItemToJson);
		return new ResponseEntity<String>(previousProcurementItemToJson, headers, HttpStatus.OK);
	}
	private void populateProcurementItemModel(Procurement procurement,Model uiModel,HttpServletRequest httpServletRequest){
		uiModel.addAttribute("procurementItem", new ProcurementItem());
		uiModel.addAttribute("customers", Customer.findAllCustomers());
//		uiModel.addAttribute("procurements", Procurement.findAllProcurements());	
		uiModel.addAttribute("products", Product.findBarProduct());
		uiModel.addAttribute("suppliers", Supplier.findAllSuppliers());
		List<Procurement> procurements = new ArrayList<Procurement>();
		if(procurement == null){
			procurements.add(Procurement.findOpenedProcurement());
		}else{
			procurements.add(procurement);
		}
		uiModel.addAttribute("procurements",procurements);
	}
	@RequestMapping(params={"close","procurementkey"},method=RequestMethod.GET)
	public String closeProcurmentProcess(@RequestParam("procurementkey")String procurementKey,Model uiModel, HttpServletRequest httpServletRequest){
		Procurement procurement = null;
		if(StringUtils.isBlank(procurementKey)) {
			populateProcurementItemModel(procurement, uiModel, httpServletRequest);
			return "procurementprocess/procurementitems/create";
		}
		try {
			procurement =ProcurementProcess.getInstance().closeProcurementProcess(procurementKey);
		} catch (Exception e) {
			uiModel.addAttribute("exception", e);
			e.printStackTrace();
		}
		uiModel.addAttribute("message", "Procurement closed");
		populateProcurementItemModel(procurement, uiModel, httpServletRequest);
		return "procurementprocess/procurementitems/create";
	}
	@RequestMapping(value="/list",method = RequestMethod.GET)
	public String listProcurementProcess(Model uiModel){
		uiModel.addAttribute("procurements", Procurement.findAllProcurements());
		return"procurementprocess/procurements/list";
	}
	@RequestMapping(value = "/procurements/{id}", method = RequestMethod.GET)
    public String showProcurementItem(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        Procurement procurement = Procurement.findProcurement(id);
		uiModel.addAttribute("procurement", procurement);
        uiModel.addAttribute("procurementitems", ProcurementItem.findProcurementItemsByProcurement(procurement).getResultList());
        uiModel.addAttribute("itemId", id);
        return "procurementprocess/procurements/show";
    }
	@RequestMapping(value="/procurementitems/see", params={"procurementKey"})
	@ResponseBody
	public ResponseEntity<String> findProcurementItemForAjaxCall(@RequestParam("procurementKey")String procurementKey,Model model,HttpServletRequest httpServletRequest){
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content Type", "application/text; chartset=utf-8");
		String findAllProcurementItemToJson = null;
		try {
			findAllProcurementItemToJson = ProcurementProcess.getInstance().findAllProcurementItemToJson(procurementKey);
		} catch (Exception e) {
			findAllProcurementItemToJson = "";
			e.printStackTrace();
		}
		System.out.println("\n \n Find Pro Item "+findAllProcurementItemToJson);
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(findAllProcurementItemToJson, httpHeaders, HttpStatus.OK);
		return responseEntity;
	}
    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("procurementItem_dateofprocurement_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }
    
    String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        }
        catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
}
