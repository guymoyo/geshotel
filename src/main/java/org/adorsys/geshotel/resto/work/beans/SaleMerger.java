package org.adorsys.geshotel.resto.work.beans;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adorsys.geshotel.resto.domain.Sale;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.stereotype.Service;
@RooJavaBean
@RooToString
@Service
public class SaleMerger {
	private Sale originalSale;
	private List<Sale> otherSales = new ArrayList<Sale>() ;
	
	public void mergeSales(){
		try {
			SaleProcess.getInstance().mergeSales(originalSale, otherSales);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Set<Sale> addOriginalSale(String saleKey){
		Sale next = Sale.findSalesBySaleKeyEquals(saleKey).getResultList().iterator().next();
		this.originalSale = next;
		Set<Sale> sales = new HashSet<Sale>(SaleProcess.getInstance().getOpenedSales());
		sales.remove(next);
		return sales;
	}
	/**
	 * add sale to the current list and create a new one to send at view from saleprocess, without those previously added.
	 * @param saleKey
	 * @return
	 */
	public Set<Sale> addOtherSale(String saleKey){
		Sale next = Sale.findSalesBySaleKeyEquals(saleKey).getResultList().iterator().next();
		if(!this.otherSales.contains(next))		this.otherSales.add(next);
		Set<Sale> sales = new HashSet<Sale>(SaleProcess.getInstance().getOpenedSales());
		sales.removeAll(otherSales);
		sales.remove(originalSale);
		return sales;
	}
	public Set<Sale> removeOtherSale(String saleKey){
		Sale next = Sale.findSalesBySaleKeyEquals(saleKey).getResultList().iterator().next();
		this.otherSales.remove(next);
		Set<Sale> sales = new HashSet<Sale>( SaleProcess.getInstance().getOpenedSales());
		sales.removeAll(otherSales);
		sales.remove(originalSale);
		return sales;
	}
}
