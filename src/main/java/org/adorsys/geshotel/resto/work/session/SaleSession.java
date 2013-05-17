package org.adorsys.geshotel.resto.work.session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adorsys.geshotel.domain.UserAccount;
import org.adorsys.geshotel.domain.ext.IdGenerator;
import org.adorsys.geshotel.domain.security.SecurityUtil;
import org.adorsys.geshotel.resto.domain.BillOfSale;
import org.adorsys.geshotel.resto.domain.Sale;
import org.adorsys.geshotel.resto.domain.SaleItem;
import org.adorsys.geshotel.resto.work.beans.WorkEntitySession;
import org.apache.commons.lang3.RandomStringUtils;

public class SaleSession {
	private static List<WorkEntitySession<SaleItem>> saleSessions = new ArrayList<WorkEntitySession<SaleItem>>();
	private static List<WorkEntitySession<Sale>> sales = new ArrayList<WorkEntitySession<Sale>>();
	
	public static BillOfSale getBillOfSale() {
		BillOfSale billOfSale = new BillOfSale();
		List<SaleItem> entities = SaleSession.mergeActiveSessions().getEntities();
		BigDecimal totalAmount = BigDecimal.ZERO;
		for (SaleItem saleItem : entities) {
			totalAmount = totalAmount.add(saleItem.getAmount());
		}
		billOfSale.setAmountDue(BigDecimal.ZERO);
		billOfSale.setDateOfSale(new Date());
		billOfSale.setDiscount(BigDecimal.ZERO);
		billOfSale.setNote("N/A");
		billOfSale.setRestaurantName("Bar/Restau");
		billOfSale.setSaleItems(new HashSet<SaleItem>(entities));
		extracted(billOfSale, entities);
		extractedSeller(billOfSale);
		billOfSale.setSellKey(IdGenerator.generateId());
		billOfSale.setTax(new BigDecimal(19.5));
		billOfSale.setTotalAmount(totalAmount);
		return billOfSale;
	}
	private static void extracted(BillOfSale billOfSale, List<SaleItem> entities) {
		Set<SaleItem> hashSet = new HashSet<SaleItem>();
		for (SaleItem saleItem : entities) {
			SaleItem findSaleItem = SaleItem.findSaleItem(saleItem.getId());
			hashSet.add(findSaleItem);
			System.out.println("Sale Items : "+findSaleItem.toString());
		}
		billOfSale.setSaleItems(hashSet);
	}
	private static void extractedSeller(BillOfSale billOfSale) {
		UserAccount baseUser = SecurityUtil.getBaseUser();
		if(baseUser == null ) return;
		billOfSale.setSeller(baseUser.getLastName());
	}
	public static List<WorkEntitySession<SaleItem>> getActiveSaleSessions(){
		List<WorkEntitySession<SaleItem>> result = new ArrayList<WorkEntitySession<SaleItem>>();
//		if(saleSessions.isEmpty()) return null;
		for(WorkEntitySession<SaleItem> saleSession : saleSessions){
			if(saleSession.isActive) result.add(saleSession);
		}
		if(result.isEmpty()) return null;
		return result;
	}
	
	public static WorkEntitySession<SaleItem> mergeActiveSessions(){
		List<WorkEntitySession<SaleItem>> activeSaleSessions = getActiveSaleSessions();
		if(activeSaleSessions == null || activeSaleSessions.isEmpty()) return null;
		WorkEntitySession<SaleItem> mergeSaleSession = new WorkEntitySession<SaleItem>(RandomStringUtils.randomAlphanumeric(6), new Date(), new Date(), SecurityUtil.getUserName());
		List<SaleItem> arrayList = new ArrayList<SaleItem>();
		for (WorkEntitySession<SaleItem> workEntitySession : activeSaleSessions) {
			if(workEntitySession.getEntities() == null) continue ;
			arrayList.addAll(workEntitySession.getEntities());
		}
		mergeSaleSession.setEntities(arrayList);
		return mergeSaleSession;
	}
	
	public static void addSaleItem(SaleItem item) {
		List<WorkEntitySession<SaleItem>> activeSaleSessions = getActiveSaleSessions();
		for (WorkEntitySession<SaleItem> workEntitySession : activeSaleSessions) {
			List<SaleItem> entities = workEntitySession.getEntities();
			if(entities == null) {
				entities = new ArrayList<SaleItem>();
			}
			entities.add(item);
			workEntitySession.setEntities(entities);
		}
	}

	public static void updateSaleItem(SaleItem item) {
		List<WorkEntitySession<SaleItem>> activeSaleSessions = getActiveSaleSessions();
		for (WorkEntitySession<SaleItem> workEntitySession : activeSaleSessions) {
			List<SaleItem> entities = workEntitySession.getEntities();
			int count = 0;
			for (SaleItem saleItem : entities) {
				if(saleItem.equals(item)){
					entities.set(count, item);
					return;
				}
				count++;
			}
		}
	}

	public static void removeSaleItem(SaleItem item) {
		List<WorkEntitySession<SaleItem>> activeSaleSessions = getActiveSaleSessions();
		for (WorkEntitySession<SaleItem> workEntitySession : activeSaleSessions) {
			List<SaleItem> entities = workEntitySession.getEntities();
			entities.remove(item);
		}
	}

	public static void addSaleSession(WorkEntitySession<SaleItem> entitySession) {
		saleSessions.add(entitySession);
	}

	public static void updateSaleSession(WorkEntitySession<SaleItem> entitySession) {
	}

	public static void removeSaleSession(WorkEntitySession<SaleItem> entitySession) {

	}
}
