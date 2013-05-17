package org.adorsys.geshotel.web;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.adorsys.geshotel.booking.domain.InvoiceState;
import org.adorsys.geshotel.domain.UserAccount;
import org.adorsys.geshotel.domain.ext.IdGenerator;
import org.adorsys.geshotel.domain.security.SecurityUtil;
import org.adorsys.geshotel.resto.domain.BarRestauInvoice;
import org.adorsys.geshotel.resto.domain.BarRestauPayment;
import org.adorsys.geshotel.resto.domain.BarRestauState;
import org.adorsys.geshotel.resto.domain.CashRegister;
import org.adorsys.geshotel.resto.domain.Sale;
import org.adorsys.geshotel.resto.work.beans.CashCollectedQueryBean;
import org.adorsys.geshotel.resto.work.beans.InvalidCashCollectedException;
import org.adorsys.geshotel.resto.work.beans.PaymentProcess;
import org.adorsys.geshotel.resto.work.exception.ActivateOpenedCashRegisterException;
import org.adorsys.geshotel.resto.work.exception.CloseClosedCashRegisterException;
import org.adorsys.geshotel.resto.work.exception.InvalidDateIntervalException;
import org.adorsys.geshotel.resto.work.exception.InvalidPaymentException;
import org.adorsys.geshotel.resto.work.exception.NoOpenedCashRegisterException;
import org.adorsys.geshotel.resto.work.exception.SuspendClosedCashRegisterException;
import org.adorsys.geshotel.resto.work.exception.SuspendSupendeCashRegisterException;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;
/**
 * @author w2b
 *
 */
@Controller
@RequestMapping(value="/paymentprocess")
public class PaymentProcessController {
	/**
	 * The @PathVariable("invoiceNumber") String invoiceNumber, in the controller must come first before the 
	 * @Valid BarRestauPayment barRestauPayment annotation.
	 * @param invoiceNumber
	 * @param barRestauPayment
	 * @param bindingResult
	 * @param uiModel
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value="/barrestaupayments/{invoiceNumber}")
    public String createBarRestauPayment(@PathVariable("invoiceNumber") String invoiceNumber,@Valid BarRestauPayment barRestauPayment, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
        	return exceptionHandled(uiModel, httpServletRequest, barRestauPayment,null);
        }
        uiModel.asMap().clear();
        try {
			PaymentProcess.getInstance().storePayment(barRestauPayment, invoiceNumber);
		} catch (InvalidPaymentException e) {
			return exceptionHandled(uiModel, httpServletRequest, barRestauPayment,e);
		}catch (NoOpenedCashRegisterException e) {
			return exceptionHandled(uiModel, httpServletRequest, barRestauPayment,e);
		}catch (Exception e) {
			return exceptionHandled(uiModel, httpServletRequest, barRestauPayment,e);
		}finally{
			System.gc();
		}
        System.out.println("\n \n Invoice Number : "+invoiceNumber);
        return "redirect:/paymentprocess/barrestaupayments/" + encodeUrlPathSegment(barRestauPayment.getId().toString(), httpServletRequest);
    }
	private String exceptionHandled(Model uiModel,HttpServletRequest httpServletRequest,BarRestauPayment barRestauPayment,Exception exception){
		uiModel.addAttribute("barRestauPayment", barRestauPayment);
		Collection<Sale> sales = new ArrayList<Sale>();
		sales.add(barRestauPayment.getSale());
		uiModel.addAttribute("sales",sales);
		if(exception != null)
		uiModel.addAttribute("exceptionClassName", exception.getClass().getName());
		uiModel.addAttribute("barrestauinvoice", ((InvalidPaymentException) exception).getBarRestauInvoice());
		uiModel.addAttribute("exception", exception);
		List<CashRegister> cashRegisters = new ArrayList<CashRegister>();
		cashRegisters.add(CashRegister.findOpenedCashRegister());
		uiModel.addAttribute("cashregisters",cashRegisters);
        return "paymentprocess/barrestaupayments/create";
	}
    
	@RequestMapping(params = "new", method = RequestMethod.GET,value="/barrestaupayments")
    public String createBarRestauPaymentForm(Model uiModel,HttpServletRequest httpServletRequest) {
        List<BarRestauInvoice> openedInvoices = BarRestauInvoice.findBarRestauInvoicesByInvoiceState(InvoiceState.Ouvert).getResultList();
//        uiModel.addAttribute("barRestauInvoices", openedInvoices);
//        uiModel.addAttribute("barRestauPayment", new BarRestauPayment());
        return "redirect:/paymentprocess/barrestauinvoices?"+encodeUrlPathSegment("find=ByInvoiceNumberEquals&form", httpServletRequest);
    }
    
    @RequestMapping(value = "/barrestaupayments/{id}", method = RequestMethod.GET)
    public String showBarRestauPayment(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("barrestaupayment", BarRestauPayment.findBarRestauPayment(id));
        uiModel.addAttribute("itemId", id);
        return "paymentprocess/barrestaupayments/show";
    }

    @RequestMapping(params = { "find=ByInvoiceNumberEquals", "form" }, value = "/barrestauinvoices",method = RequestMethod.GET)
    public String findBarRestauInvoicesByInvoiceNumberEqualsForm(Model uiModel) {
        return "paymentprocess/barrestauinvoices/findBarRestauInvoicesByInvoiceNumberEquals";
    }
    
    @RequestMapping(params = "find=ByInvoiceNumberEquals", value="/barrestauinvoices",method = RequestMethod.GET)
    public String findBarRestauInvoicesByInvoiceNumberEquals(@RequestParam("invoiceNumber") String invoiceNumber, Model uiModel) {
        BarRestauInvoice barrestauinvoice = null;
        BarRestauPayment barRestauPayment = new BarRestauPayment();
        List<Sale> sales = new ArrayList<Sale>();
		try {
			barrestauinvoice = PaymentProcess.getInstance().getInvoiceToPay(invoiceNumber);
			sales = PaymentProcess.getInstance().getSales(barrestauinvoice);
			barRestauPayment.setAmountDue(sales.iterator().next().getTotalAmount());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			System.gc();
		}
        uiModel.addAttribute("sales", sales);
        uiModel.addAttribute("barRestauPayment", barRestauPayment);
		uiModel.addAttribute("barrestauinvoice", barrestauinvoice);
		List<CashRegister> cashRegisters = new ArrayList<CashRegister>();
		cashRegisters.add(CashRegister.findOpenedCashRegister());
		System.out.println("\n \n Cash register \t "+cashRegisters);
		uiModel.addAttribute("cashregisters",cashRegisters);
        return "paymentprocess/barrestaupayments/create";
    }
    @RequestMapping(value="/cashregisters",method=RequestMethod.GET)
    public String seeCashRegister(@RequestParam(value="action",required=true)String action,HttpServletRequest httpServletRequest,Model uiModel ){
    	List<CashRegister> cashRegisters = null;
        	try {
    			cashRegisters= PaymentProcess.getInstance().findActiveCashRegister(SecurityUtil.getBaseUser());
    		} catch (NullPointerException e) {
    			e.printStackTrace();
    		}
        	if(cashRegisters == null || cashRegisters.isEmpty()){
        		uiModel.addAttribute("cashregister", null);
        	}else{
        		uiModel.addAttribute("cashregister", cashRegisters.iterator().next());
        	}
        	return "paymentprocess/cashregisters/show";
    }
    @RequestMapping(value="/cashregisters/list",method = RequestMethod.GET)
    public String listCashRegisters(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("cashregisters", CashRegister.findCashRegisterEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) CashRegister.countCashRegisters() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("cashregisters", CashRegister.findAllCashRegisters());
        }
        addDateTimeFormatPatterns(uiModel);
        return "cashregisters/list";
    }
    @RequestMapping(value="/cashregisters",params="new")
    public String cashRegisterCreateForm(Model uiModel,HttpServletRequest request){
    	CashRegister cashRegister = new CashRegister();
    	cashRegister.setCashierAccount(SecurityUtil.getBaseUser());
    	cashRegister.setCashRegisterKey(IdGenerator.generateId());
    	cashRegister.setCashRegisterState(BarRestauState.SUSPENDED);
    	uiModel.addAttribute("cashRegister", cashRegister);
    	uiModel.addAttribute("cashregisterstates", Arrays.asList(BarRestauState.class.getEnumConstants()));
    	uiModel.addAttribute("useraccounts", new ArrayList<UserAccount>(Arrays.asList(SecurityUtil.getBaseUser())));
    	return "paymentprocess/cashregisters/create";
    }

    @RequestMapping(value="/cashregisters/create",method = RequestMethod.POST)
    public String create( CashRegister cashRegister, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
    	
        /*if (bindingResult.hasErrors()) {
            uiModel.addAttribute("cashRegister", cashRegister);
            addDateTimeFormatPatterns(uiModel);
            return "paymentprocess/cashregisters/create";
        }*/
        uiModel.asMap().clear();
        try {
			PaymentProcess.getInstance().saveCashRegister(cashRegister);
		} catch (Exception e) {
			e.printStackTrace();
		}
        System.out.println(" \n \n Cash Register Opened ");
        return "redirect:/paymentprocess/cashregisters/" + encodeUrlPathSegment(cashRegister.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/cashregisters/{id}", method = RequestMethod.GET)
    public String showCashRegister(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("cashregister", CashRegister.findCashRegister(id));
        uiModel.addAttribute("itemId", id);
        return "paymentprocess/cashregisters/show";
    }
    @RequestMapping(value="/cashregisters",method=RequestMethod.GET,params="suspend")
    public String suspendCashRegister(@RequestParam("suspend")String cashRegisterKey,Model uiModel,HttpServletRequest httpServletRequest){
    	CashRegister cashRegister = null;
    	try {
			cashRegister = PaymentProcess.getInstance().suspendCashRegister(cashRegisterKey);
		} catch (SuspendSupendeCashRegisterException e) {
			populateCashRegisterModel(cashRegisterKey, uiModel, httpServletRequest, e);
			e.printStackTrace();
		} catch (SuspendClosedCashRegisterException e) {
			populateCashRegisterModel(cashRegisterKey, uiModel, httpServletRequest, e);
			e.printStackTrace();
		}

        return "redirect:/paymentprocess/cashregisters/" + encodeUrlPathSegment(cashRegister.getId().toString(), httpServletRequest);
    }
    @RequestMapping(value="/cashregisters",method=RequestMethod.GET,params="activate")
    public String activateCashRegister(@RequestParam("activate")String cashRegisterKey,Model uiModel,HttpServletRequest httpServletRequest){
    	CashRegister cashRegister = null;
    	try {
			cashRegister = PaymentProcess.getInstance().activateCashRegister(cashRegisterKey);
		} catch (ActivateOpenedCashRegisterException e) {
			populateCashRegisterModel(cashRegisterKey, uiModel, httpServletRequest, e);
			e.printStackTrace();
		}
        return "redirect:/paymentprocess/cashregisters/" + encodeUrlPathSegment(cashRegister.getId().toString(), httpServletRequest);
    }
    @RequestMapping(value="/cashregisters",method=RequestMethod.GET,params="close")
    public String closeCashRegister(@RequestParam("close")String cashRegisterKey,Model uiModel,HttpServletRequest httpServletRequest){
    	CashRegister cashRegister = null;
    	try {
			cashRegister = PaymentProcess.getInstance().closeCashRegister(cashRegisterKey);
		} catch (CloseClosedCashRegisterException e) {
			populateCashRegisterModel(cashRegisterKey, uiModel, httpServletRequest, e);
			e.printStackTrace();
		}
        return "redirect:/paymentprocess/cashregisters/" + encodeUrlPathSegment(cashRegister.getId().toString(), httpServletRequest);
    }
    @RequestMapping(value="/cashregisters",params="query=cashcollected",method=RequestMethod.GET)
    public String queryCashCollected(Model uiModel,HttpServletRequest httpServletRequest){
    	CashCollectedQueryBean cashCollectedQueryBean = new CashCollectedQueryBean();
    	uiModel.addAttribute("cashCollectedQueryBean", cashCollectedQueryBean);
    	uiModel.addAttribute("querybean_date_format", "dd-MM-yyyy HH:mm");
    	return "paymentprocess/statistics/querycashcollectedform";
    }
    
    @RequestMapping(value="/cashregisters/cashcollected",method=RequestMethod.POST)
    public String findCashCollected(@Valid CashCollectedQueryBean cashCollectedQueryBean,BindingResult bindingResult,Model uiModel,HttpServletRequest httpServletRequest){
    	if(bindingResult.hasErrors()){
    		uiModel.addAttribute("cashCollectedQueryBean", cashCollectedQueryBean);
    		uiModel.addAttribute("querybean_date_format", "dd-MM-yyyy HH:mm");
    		return "paymentprocess/statistics/querycashcollectedform";
    	}
    	uiModel.addAttribute("cashCollectedQueryBean", cashCollectedQueryBean);
		uiModel.addAttribute("querybean_date_format", "dd-MM-yyyy HH:mm");
    	try {
			BigDecimal cashCallected = PaymentProcess.getInstance().cashCallected(cashCollectedQueryBean.getCollectedFrom(), cashCollectedQueryBean.getCollectedTo());
			uiModel.addAttribute("cashCollected", cashCallected);
		} catch (InvalidDateIntervalException e) {
			e.printStackTrace();
			uiModel.addAttribute("exception", e);
			return "paymentprocess/statistics/querycashcollectedform";
		} catch (InvalidCashCollectedException e) {
			uiModel.addAttribute("exception", e);
			e.printStackTrace();
		}
    	return "paymentprocess/statistics/querycashcollectedform";
    }
    private void populateCashRegisterModel(String cashRegisterKey,Model uiModel,HttpServletRequest httpServletRequest, Exception exception){
    	uiModel.addAttribute("cashregister", CashRegister.findCashRegistersByCashRegisterKeyEquals(cashRegisterKey).getSingleResult());
    	uiModel.addAttribute("exception", exception);
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
    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("cashRegister_dateofopening_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("cashRegister_dateofclosing_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }
}

