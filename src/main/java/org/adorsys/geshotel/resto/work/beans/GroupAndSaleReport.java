/**
 * 
 */
package org.adorsys.geshotel.resto.work.beans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.adorsys.geshotel.resto.domain.ProductGroup;
import org.adorsys.geshotel.resto.domain.SaleItem;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * @author w2b
 *
 */
@RooJavaBean
@RooToString
public class GroupAndSaleReport {
	private ProductGroup productGroup;
	private List<SaleItem> saleItems = new ArrayList<SaleItem>();
	private BigDecimal serviceAmount = BigDecimal.ZERO;
}