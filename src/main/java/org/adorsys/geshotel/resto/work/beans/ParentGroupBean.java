package org.adorsys.geshotel.resto.work.beans;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

import org.adorsys.geshotel.resto.domain.ParentGroup;
import org.adorsys.geshotel.resto.domain.ProductGroup;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class ParentGroupBean {
	@NotNull
	private String parentGroupKey;
	@NotNull
	private String productGroupKey;
	private String productGroupParentKey;
	private String productGroupTitle;
	private String description;
	/**
	 * used to send all product group titles to the view.
	 */
	private List<String> allProductGroupKeys;
	
	public ParentGroup newProductGroup(){
		ParentGroup parentGroup = new ParentGroup();
		parentGroup.setDescription(description);
		parentGroup.setParentGroupKey(parentGroupKey);
		parentGroup.setProductGroupKey(productGroupKey);
		TypedQuery<ProductGroup> typedQuery = ProductGroup.findProductGroupsByTitleEquals(productGroupTitle);
		if(typedQuery.getResultList().isEmpty()){
			this.productGroupParentKey = "N/A";
		}else{
			this.productGroupParentKey = typedQuery.getResultList().iterator().next().getProductGroupKey();
		}
		parentGroup.setProductGroupParentKey(productGroupParentKey);
		return parentGroup;
	}
}
