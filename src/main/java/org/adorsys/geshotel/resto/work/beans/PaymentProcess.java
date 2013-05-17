package org.adorsys.geshotel.resto.work.beans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.adorsys.geshotel.booking.domain.InvoiceState;
import org.adorsys.geshotel.domain.UserAccount;
import org.adorsys.geshotel.domain.ext.IdGenerator;
import org.adorsys.geshotel.domain.security.SecurityUtil;
import org.adorsys.geshotel.resto.domain.BarRestauInvoice;
import org.adorsys.geshotel.resto.domain.BarRestauPayment;
import org.adorsys.geshotel.resto.domain.BarRestauState;
import org.adorsys.geshotel.resto.domain.CashRegister;
import org.adorsys.geshotel.resto.domain.CustomerType;
import org.adorsys.geshotel.resto.domain.Sale;
import org.adorsys.geshotel.resto.work.exception.ActivateOpenedCashRegisterException;
import org.adorsys.geshotel.resto.work.exception.CloseClosedCashRegisterException;
import org.adorsys.geshotel.resto.work.exception.InvalidDateIntervalException;
import org.adorsys.geshotel.resto.work.exception.InvalidPaymentException;
import org.adorsys.geshotel.resto.work.exception.NoOpenedCashRegisterException;
import org.adorsys.geshotel.resto.work.exception.SuspendClosedCashRegisterException;
import org.adorsys.geshotel.resto.work.exception.SuspendSupendeCashRegisterException;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

public class PaymentProcess {
	private List<BarRestauPayment> barRestauPaiments = new ArrayList<BarRestauPayment>();

	private static PaymentProcess paymentProcess;
	private static Object synchObject__ = new Object();

	public static PaymentProcess getInstance() {
		if (paymentProcess == null) {
			synchronized (synchObject__) {
				if (paymentProcess == null) {
					return new PaymentProcess();
				}
			}
		}
		return paymentProcess;
	}

	public List<Sale> getSales(List<BarRestauInvoice> barRestauInvoices) {
		List<Sale> sales = new ArrayList<Sale>();
		Assert.notNull(barRestauInvoices, "Invoices may not be null here");
		for (BarRestauInvoice barRestauInvoice : barRestauInvoices) {
			sales.add(barRestauInvoice.getSale());
		}
		return sales;
	}

	public List<Sale> getSales(BarRestauInvoice barRestauInvoice) {
		List<Sale> sales = new ArrayList<Sale>();
		Assert.notNull(barRestauInvoice, "Invoices may not be null");
		sales.add(barRestauInvoice.getSale());
		return sales;
	}

	public void storePayment(BarRestauPayment barRestauPayment,String invoiceNumber) throws InvalidPaymentException, NoOpenedCashRegisterException {
		BarRestauInvoice barRestauInvoice = BarRestauInvoice.findBarRestauInvoicesByInvoiceNumberEquals(invoiceNumber).getSingleResult();
		storePayment(barRestauPayment, barRestauInvoice);
	}
	@Transactional
	public void storePayment(BarRestauPayment barRestauPayment,BarRestauInvoice barRestauInvoice) throws InvalidPaymentException, NoOpenedCashRegisterException {
		List<CashRegister> activeCashRegisters = findActiveCashRegister(SecurityUtil.getBaseUser());
		if(activeCashRegisters.isEmpty()) throw new NoOpenedCashRegisterException("You Might Open a cash register, to complete this process");
		barRestauPayment.setAmountDue(barRestauInvoice.getSale().getTotalAmount());
		BigDecimal amountDue = barRestauPayment.getAmountDue();
		BigDecimal amountIn = barRestauPayment.getAmountIn();
		int compareTo = amountDue.compareTo(amountIn);
		BigDecimal amountOut = BigDecimal.ZERO;
		
		switch (compareTo) {
		case 1:
			throw new InvalidPaymentException("Invalid Payment Occurs. Check If the Amount Is smaller that The amount out !",barRestauInvoice)	;
		case -1:
			amountOut = amountOut.add(amountIn.subtract(amountDue));
			barRestauPayment.setAmountOut(amountOut);
			break ;
		case 0:
			barRestauPayment.setAmountOut(BigDecimal.ZERO);
			break;
		default:
			break;
		}
		barRestauInvoice.setInvoiceState(InvoiceState.Ferme);
		barRestauInvoice.merge().flush();
		barRestauPayment.setCashRegister(activeCashRegisters.iterator().next());
		barRestauPayment.persist();
		barRestauPayment.flush();
	}

	public BarRestauInvoice getInvoiceToPay(String invoiceNumber) {
		BarRestauInvoice barrestauinvoice = BarRestauInvoice
				.findBarRestauInvoicesByInvoiceNumberEquals(invoiceNumber)
				.getSingleResult();
		if (barrestauinvoice.getInvoiceState().equals(InvoiceState.Ouvert)) {
			return barrestauinvoice;
		}
		throw new NullPointerException("No Unpaid Invoice Found");
	}
	@SuppressWarnings("unchecked")
	public List<CashRegister>  findActiveCashRegister(UserAccount account)throws NullPointerException{
		if(account == null){
			//try to get the logget user Account.
			account = SecurityUtil.getBaseUser();
		}
		if(account == null) throw new IllegalStateException("No user Found to process this request");
		List<CashRegister> openedCashRegisters = CashRegister.findUserCashRegister(BarRestauState.OPENED, account).getResultList();
		List<CashRegister> suspendedCashRegisters = CashRegister.findUserCashRegister(BarRestauState.SUSPENDED, account).getResultList();
		
		return ListUtils.sum(openedCashRegisters, suspendedCashRegisters);
	}
	public CashRegister saveCashRegister(CashRegister cashRegister)throws NullPointerException{
		if(cashRegister == null) throw new NullPointerException("The Cash Register might not be null");
		List<CashRegister> activeCashRegisters = findActiveCashRegister(SecurityUtil.getBaseUser());
		if(!activeCashRegisters.isEmpty()) {
			cashRegister = activeCashRegisters.iterator().next();
			return cashRegister;
		}
		cashRegister.setCashierAccount(SecurityUtil.getBaseUser());
		cashRegister.setCashRegisterKey(IdGenerator.generateId());
		cashRegister.setCashRegisterState(BarRestauState.OPENED);
		cashRegister.setDateOfOpening(new Date());
		cashRegister.persist();
		return cashRegister;
	}
	public CashRegister suspendCashRegister(String cashRegisterKey)throws SuspendSupendeCashRegisterException, SuspendClosedCashRegisterException{
		Assert.notNull(cashRegisterKey, "The CashRegister might not be Null");
		CashRegister cashRegister = CashRegister.findCashRegistersByCashRegisterKeyEquals(cashRegisterKey).getSingleResult();
		if(BarRestauState.OPENED == cashRegister.getCashRegisterState()) {
			cashRegister.setCashRegisterState(BarRestauState.SUSPENDED);
			cashRegister.merge().flush();
			return cashRegister ;
		}
		if(BarRestauState.SUSPENDED == cashRegister.getCashRegisterState()) throw new SuspendSupendeCashRegisterException("The Cash Register is already suspended.");
		if(BarRestauState.CLOSED == cashRegister.getCashRegisterState())throw new SuspendClosedCashRegisterException("You need to Open a CashRegister .");
		return null;
	}
	public CashRegister activateCashRegister(String cashRegisterKey)throws  ActivateOpenedCashRegisterException	{
		if(StringUtils.isBlank(cashRegisterKey)) throw new IllegalStateException("Please Provide  a Correct cash register Key");
		CashRegister cashRegister = CashRegister.findCashRegistersByCashRegisterKeyEquals(cashRegisterKey).getSingleResult();
		if(BarRestauState.SUSPENDED == cashRegister.getCashRegisterState()){
			cashRegister.setCashRegisterState(BarRestauState.OPENED);
			cashRegister.merge().flush();
			return cashRegister;
		}
		if(BarRestauState.OPENED == cashRegister.getCashRegisterState()) throw new ActivateOpenedCashRegisterException("The Cash Register Is already Opened");
		return null;
	}
	public CashRegister closeCashRegister(String cashRegisterKey)throws CloseClosedCashRegisterException{
		if(StringUtils.isBlank(cashRegisterKey)) throw new IllegalStateException("Please Provide  a Correct cash register Key");
		CashRegister cashRegister = CashRegister.findCashRegistersByCashRegisterKeyEquals(cashRegisterKey).getSingleResult();
		if(BarRestauState.CLOSED != cashRegister.getCashRegisterState()){
			cashRegister.setCashRegisterState(BarRestauState.CLOSED);
			cashRegister.setDateOfClosing(new Date());
			cashRegister.merge().flush();
			return cashRegister;
		}else throw new CloseClosedCashRegisterException("The cash register must be opened, In order to complete this request.");
	}
	
	public BigDecimal cashCallected(Date collectedFrom,Date collectedTo) throws InvalidDateIntervalException, InvalidCashCollectedException{
		checkDates(collectedFrom, collectedTo);
		List<CashRegister> cashRegisters = CashRegister.findCashRegisterByPeriod(collectedFrom, collectedTo).getResultList();
		BigDecimal cashCollected = BigDecimal.ZERO;
		for (CashRegister cashRegister : cashRegisters) {
			BigDecimal cashCollectedByCashRegister = BarRestauPayment.cashCollectedByCashRegister(cashRegister);
			cashCollected = cashCollected.add(cashCollectedByCashRegister);
		}
		//ajoute le montant des ventes donc les factures sont allees au service de la reservation.
		cashCollected = cashCollected.add(sumHostedCustomerSales(collectedFrom, collectedTo));
		System.out.println("\n \n Cash Collected  : "+cashCollected);
		return cashCollected;	
	}

	private void checkDates(Date collectedFrom, Date collectedTo)
			throws InvalidDateIntervalException {
		if(collectedFrom.after(collectedTo)) throw new InvalidDateIntervalException("The collected From Date "+collectedFrom+
				" might be before the collected To "+collectedTo);
	}
	public BigDecimal sumHostedCustomerSales(Date collectedFrom,Date collectedTo) throws InvalidDateIntervalException{
		checkDates(collectedFrom, collectedTo);
//		List<Sale> sales = Sale.findSalesByRecordDateBetweenAndSaleState(collectedFrom, collectedTo, BarRestauState.CLOSED).getResultList();
		List<Sale> hostedCustomerSales = Sale.findSalesByRecordDateBetweenAndSaleStateAndCustomerType(collectedFrom, collectedTo, BarRestauState.CLOSED, CustomerType.HOSTED_CUSTOMER).getResultList();
		BigDecimal hostedCustomerSaleAmount = BigDecimal.ZERO;
		for (Sale sale : hostedCustomerSales) {
			hostedCustomerSaleAmount = hostedCustomerSaleAmount.add(sale.getTotalAmount());
		}
		return hostedCustomerSaleAmount;
	}
}