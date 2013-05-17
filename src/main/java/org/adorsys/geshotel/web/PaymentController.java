package org.adorsys.geshotel.web;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.adorsys.geshotel.booking.domain.Invoice;
import org.adorsys.geshotel.booking.domain.PayementState;
import org.adorsys.geshotel.booking.domain.Retrait;
import org.adorsys.geshotel.domain.PayementType;
import org.adorsys.geshotel.domain.Payment;
import org.adorsys.geshotel.domain.UserAccount;
import org.adorsys.geshotel.domain.security.SecurityUtil;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RooWebScaffold(path = "payments", formBackingObject = Payment.class)
@RequestMapping("/payments")
@Controller
public class PaymentController {

	@RequestMapping(value = "/pay", method = RequestMethod.GET)
	public String payBill(
			@RequestParam(value = "montant", required = true) Long montant,
			@RequestParam(value = "invoice", required = true) Long invoice,
			@RequestParam(value = "type") String type,
			@RequestParam(value = "note", required = false) String note,
			Model uiModel, HttpServletRequest httpServletRequest) {

		Invoice findInvoice = Invoice.findInvoice(invoice);

		Payment payment = new Payment();
		payment.setCasheir(SecurityUtil.getBaseUser());
		payment.setDatePayemment(new Date());
		payment.setInvoice(findInvoice);
		payment.setAmount(new BigDecimal(montant));
		payment.setNote(note);
		payment.setPayementType(PayementType.valueOf(type));
		payment.persist();
		payment.flush();

		findInvoice.postLoadFunction();
		System.out.print(findInvoice.getAmountPaid());
		System.out.print("due " + findInvoice.getAmountDue());

		if (findInvoice.getAmountPaid().compareTo(findInvoice.getAmountDue()) == 1
				|| payment.getAmount().compareTo(new BigDecimal(0)) == 0) {
			payment.remove();
			return "redirect:/invoices/facture?invoiceId=" + invoice;
		}

		if (payment.getPayementType() == PayementType.COMPTE) {
			  
			if(payment.getAmount().compareTo(findInvoice.getCustomer().getAccount())  == 1){
				
				payment.remove();
				return "redirect:/invoices/facture?invoiceId=" + invoice;
			}else{
				new Retrait("payment", payment.getAmount(), new Date(), SecurityUtil.getBaseUser(), findInvoice.getCustomer()).persist();
			}
		}

		if (findInvoice.getAmountPaid().compareTo(findInvoice.getAmountDue()) == 0) {
			findInvoice.setPayementState(PayementState.Pay);

		} else {
			findInvoice.setPayementState(PayementState.Advanced);

		}
		findInvoice.merge();
		findInvoice.flush();

		return "redirect:/invoices/facture?invoiceId=" + invoice;
	}

	@RequestMapping("/collect")
	public String collect(Model uiModel, HttpServletRequest httpServletRequest) {
		Date date = new Date();
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		Date date2 = new Date();
		date2.setHours(23);
		date2.setMinutes(59);
		date2.setSeconds(59);
		List<Payment> listp = Payment
				.findPaymentsByCasheirAndDatePayemmentBetween(
						SecurityUtil.getBaseUser(), date, date2)
				.getResultList();
		BigDecimal montant = BigDecimal.ZERO;
		for (Payment p : listp) {
			montant = montant.add(p.getAmount());
		}
		uiModel.addAttribute("montant", montant);
		uiModel.addAttribute("payments", listp);
		addDateTimeFormatPatterns(uiModel);
		return "payments/list";
	}
	
	@RequestMapping("/collectAll")
	public String collectAll(Model uiModel, HttpServletRequest httpServletRequest) {
		Date date = new Date();
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		Date date2 = new Date();
		date2.setHours(23);
		date2.setMinutes(59);
		date2.setSeconds(59);
		List<Payment> listp = Payment.findPaymentsByDatePayemmentBetween(
				date, date2).getResultList();
		BigDecimal montant = BigDecimal.ZERO;
		for (Payment p : listp) {
			montant = montant.add(p.getAmount());
		}
		uiModel.addAttribute("montant", montant);
		uiModel.addAttribute("payments", listp);
		addDateTimeFormatPatterns(uiModel);
		return "payments/list";
	}

	@RequestMapping(params = "find=ByCasheirAndDatePayemmentBetween", method = RequestMethod.GET)
	public String findPaymentsByCasheirAndDatePayemmentBetween(
			@RequestParam("casheir") UserAccount casheir,
			@RequestParam("minDatePayemment") @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm:ss") Date minDatePayemment,
			@RequestParam("maxDatePayemment") @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm:ss") Date maxDatePayemment,
			Model uiModel) {

		List<Payment> listp = Payment
				.findPaymentsByCasheirAndDatePayemmentBetween(casheir,
						minDatePayemment, maxDatePayemment).getResultList();
		BigDecimal montant = BigDecimal.ZERO;
		for (Payment p : listp) {
			montant = montant.add(p.getAmount());
		}
		uiModel.addAttribute("montant", montant);
		uiModel.addAttribute("payments", listp);
		addDateTimeFormatPatterns(uiModel);
		return "payments/list";
	}

	@RequestMapping(params = "find=ByDatePayemmentBetween", method = RequestMethod.GET)
	public String findPaymentsByDatePayemmentBetween(
			@RequestParam("minDatePayemment") @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm:ss") Date minDatePayemment,
			@RequestParam("maxDatePayemment") @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm:ss") Date maxDatePayemment,
			Model uiModel) {
		List<Payment> listp = Payment.findPaymentsByDatePayemmentBetween(
				minDatePayemment, maxDatePayemment).getResultList();
		BigDecimal montant = BigDecimal.ZERO;
		for (Payment p : listp) {
			montant = montant.add(p.getAmount());
		}
		uiModel.addAttribute("montant", montant);
		uiModel.addAttribute("payments", listp);
		addDateTimeFormatPatterns(uiModel);
		return "payments/list";
	}

}
