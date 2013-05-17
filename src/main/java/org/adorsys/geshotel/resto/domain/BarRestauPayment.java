package org.adorsys.geshotel.resto.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.adorsys.geshotel.resto.work.beans.InvalidCashCollectedException;
import org.adorsys.geshotel.resto.work.exception.InvalidDateIntervalException;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.util.Assert;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findBarRestauPaymentsByCashRegister" })
public class BarRestauPayment {

    @ManyToOne
    private Sale sale;

    private BigDecimal amountDue;

    private BigDecimal amountIn;

    private BigDecimal amountOut;

    private String note;

    @NotNull
    @ManyToOne
    private CashRegister cashRegister;

	public static List<BarRestauPayment> findCashRegisterPayments(Date collectedFrom,Date collectedTo) throws InvalidDateIntervalException{
		if(collectedFrom.after(collectedTo)) throw new InvalidDateIntervalException("The collected From Date "+collectedFrom+
				" might be before the collected To "+collectedTo);
		List<CashRegister> cashRegisters = CashRegister.findCashRegisterByPeriod(collectedFrom, collectedTo).getResultList();
		Assert.notNull(cashRegisters, "Null list result,");
		List<BarRestauPayment> barRestauPayments = new ArrayList<BarRestauPayment>();
		for (CashRegister cashRegister : cashRegisters) {
			List<BarRestauPayment> cashRegisterBarRestauPayments = BarRestauPayment.findBarRestauPaymentsByCashRegister(cashRegister).getResultList();
			if(cashRegisterBarRestauPayments != null &&  !cashRegisterBarRestauPayments.isEmpty() ) barRestauPayments.addAll(cashRegisterBarRestauPayments);
		}
		return barRestauPayments;
	}
	public static BigDecimal cashCollectedByCashRegister(CashRegister cashRegister) throws InvalidCashCollectedException{
		List<BarRestauPayment> barRestauPayments = BarRestauPayment.findBarRestauPaymentsByCashRegister(cashRegister).getResultList();
		BigDecimal cashCollected = BigDecimal.ZERO;
		for (BarRestauPayment barRestauPayment : barRestauPayments) {
			cashCollected = cashCollected.add(barRestauPayment.getAmountDue());
		}
		//retire le montant d'initialisation de la caisse.
		cashCollected.subtract(cashRegister.getCashFund());
		if(cashCollected.doubleValue() < 0.0) throw new InvalidCashCollectedException(cashCollected, cashRegister);
		return cashCollected;
	}
}
