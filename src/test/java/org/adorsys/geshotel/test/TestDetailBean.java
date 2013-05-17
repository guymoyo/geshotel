package org.adorsys.geshotel.test;

import java.util.ArrayList;
import java.util.List;

import org.adorsys.geshotel.resto.domain.ProcurementItem;
import org.adorsys.geshotel.resto.domain.ProcurementItemDataOnDemand;
import org.adorsys.geshotel.resto.domain.Sale;
import org.adorsys.geshotel.resto.domain.SaleDataOnDemand;
import org.adorsys.geshotel.resto.domain.SaleItem;
import org.adorsys.geshotel.resto.domain.SaleItemDataOnDemand;
import org.adorsys.geshotel.resto.work.beans.DetailedSaleBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/*@RunWith(value=SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:META-INF/spring/applicationContext*.xml"})
@Transactional*/
public class TestDetailBean {/*
	@Test
	public void testToJson(){
		SaleDataOnDemand saleDataOnDemand = new SaleDataOnDemand();
		SaleItemDataOnDemand saleItemDataOnDemand = new SaleItemDataOnDemand();
		Sale randomSale = saleDataOnDemand.getRandomSale();
		List<SaleItem> arrayList = new ArrayList<SaleItem>();
		arrayList.add(saleItemDataOnDemand.getRandomSaleItem());
		arrayList.add(saleItemDataOnDemand.getRandomSaleItem());
		DetailedSaleBean detailedSaleBean = new DetailedSaleBean();
		detailedSaleBean.setSale(randomSale);
		detailedSaleBean.setSaleItems(arrayList);
		System.out.println(arrayList);
		String json = detailedSaleBean.getSaleItemsToJSon();
		System.out.println(json);
	}
	@Test
	public void testProcurementItemToJson(){
		ProcurementItemDataOnDemand procurementItemDataOnDemand = new ProcurementItemDataOnDemand();
		ProcurementItem randomProcurementItem = procurementItemDataOnDemand.getRandomProcurementItem();
		System.out.println("Random Procurment Item "+randomProcurementItem.toJson());
	}*/
}
