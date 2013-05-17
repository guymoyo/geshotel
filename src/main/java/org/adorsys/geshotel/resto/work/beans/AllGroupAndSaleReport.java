package org.adorsys.geshotel.resto.work.beans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.adorsys.geshotel.resto.domain.ProductGroup;
import org.springframework.roo.addon.javabean.RooJavaBean;
@RooJavaBean
public class AllGroupAndSaleReport {
	private List<GroupAndSaleReport> groupAndSaleReports = new ArrayList<GroupAndSaleReport>();
	private BigDecimal saleAmount = BigDecimal.ZERO;
	public static GroupAndSaleReport findAGroupReport(AllGroupAndSaleReport allGroupAndSaleReport,ProductGroup group){
		List<GroupAndSaleReport> groupAndSaleReports2 = allGroupAndSaleReport.getGroupAndSaleReports();
		for (GroupAndSaleReport groupAndSaleReport : groupAndSaleReports2) {
			if(groupAndSaleReport.getProductGroup().getId().equals(group.getId())) return groupAndSaleReport;
		}
		return null;
	}
}
