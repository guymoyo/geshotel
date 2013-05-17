package org.adorsys.geshotel.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.adorsys.geshotel.resto.domain.BarRestauState;
import org.adorsys.geshotel.resto.domain.Product;
import org.adorsys.geshotel.resto.domain.ProductGroup;
import org.adorsys.geshotel.resto.domain.ProductType;
import org.adorsys.geshotel.resto.work.exception.NoParentGroupFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RooWebScaffold(path = "products", formBackingObject = Product.class)
@RequestMapping("/products")
@Controller
public class ProductController {
	
	@Autowired
	private Validator validator;
	
	@RequestMapping(method = RequestMethod.POST)
    public String create(@Valid Product product, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("product", product);
            return "products/create";
        }
        uiModel.asMap().clear();
        if(product.getProductType() != null){
        	try {
				product.setGroups(new HashSet<ProductGroup>(getProductGroupsByProductType(product.getProductType())));
			} catch (NoParentGroupFound e) {
				uiModel.addAttribute("exception", e);
				uiModel.addAttribute("product", product);
				e.printStackTrace();
				return "products/create";
			}
        }
        System.out.println("\n \n Check Group "+product.isBarProduct());
        product.persist();
        return "redirect:/products/" + encodeUrlPathSegment(product.getId().toString(), httpServletRequest);
    }
	
	 @RequestMapping(params = "form", method = RequestMethod.GET)
	    public String createForm(Model uiModel) {
	        uiModel.addAttribute("product", new Product());
	        List dependencies = new	 ArrayList();
	        if (ProductType.countProductTypes() == 0) {
	            dependencies.add(new String[]{"producttype", "producttypes"});
	        }
	        uiModel.addAttribute("dependencies", dependencies);
	        return "products/create";
	    }
	
	public static  List<ProductGroup> getProductGroupsByProductType(ProductType productType) throws NoParentGroupFound{
		Assert.notNull(productType, "The product type is required to get product groups");		
		List<ProductGroup> allProductGroups = ProductGroup.findAllProductGroups();
		List<ProductGroup> matchingGroups = new ArrayList<ProductGroup>();
		List<ProductType> productTypes = null;
		for (ProductGroup productGroup : allProductGroups) {
			productTypes = new ArrayList<ProductType>(productGroup.getTypes());
			for (ProductType type : productTypes) {
				if(type.equals(productType)){
					matchingGroups.add(productGroup);
					break;
				}
			}
		}
		
		List<ProductGroup> allProductGroupsParentIncluded = new ArrayList<ProductGroup>(matchingGroups);
		ProductGroup highGroup = ProductGroup.findProductGroupsByTitleEquals("DEFAULT").getResultList().iterator().next();
		for (ProductGroup productGroup : matchingGroups) {
			List<ProductGroup> parentGroupRecursively = Product.findParentGroupRecursively(productGroup, highGroup, new ArrayList<ProductGroup>());
			allProductGroupsParentIncluded.addAll(parentGroupRecursively);
		}
		System.out.println("\n \n All Matching Groups :  "+allProductGroupsParentIncluded);
		return allProductGroupsParentIncluded;
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		Product findProduct = Product.findProduct(id);
		findProduct.setProductState(BarRestauState.DISABLED);
		findProduct.merge().flush();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/products";
    }
    @RequestMapping(method = RequestMethod.PUT)
    public String update(Product product, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
    	if (bindingResult.hasErrors()) {
    		uiModel.addAttribute("product", product);
    		System.out.println("Binding has errors"+bindingResult.getAllErrors());
    		return "products/update";
    	}
    	try {
			product.setGroups(new HashSet<ProductGroup>(getProductGroupsByProductType(product.getProductType())));
		} catch (NoParentGroupFound e) {
			e.printStackTrace();
            uiModel.addAttribute("product", product);
            uiModel.addAttribute("exception", e);
            return "products/update";
		}catch (Exception e){
			e.printStackTrace();
            uiModel.addAttribute("product", product);
            uiModel.addAttribute("exception", e);
            return "products/update";
		}
        uiModel.asMap().clear();
        
        product.merge();
        return "redirect:/products/" + encodeUrlPathSegment(product.getId().toString(), httpServletRequest);
    }
    @ModelAttribute("products")
    public Collection<Product> populateProducts() {
//        return Product.findAllProducts();
    	Collection<Product> products = new ArrayList<Product>();
    	return products;
    }
}