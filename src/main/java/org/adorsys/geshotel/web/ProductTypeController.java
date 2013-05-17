package org.adorsys.geshotel.web;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.adorsys.geshotel.resto.domain.BarRestauState;
import org.adorsys.geshotel.resto.domain.ProductGroup;
import org.adorsys.geshotel.resto.domain.ProductType;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RooWebScaffold(path = "producttypes", formBackingObject = ProductType.class)
@RequestMapping("/producttypes")
@Controller
public class ProductTypeController {
	
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
//        ProductType.findProductType(id).remove();
    	ProductType findProductType = ProductType.findProductType(id);
    	findProductType.setProductTypeState(BarRestauState.DISABLED);
    	findProductType.merge().flush();
    	
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/producttypes";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(@Valid ProductType productType, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("productType", productType);
            addDateTimeFormatPatterns(uiModel);
            return "producttypes/create";
        }
        uiModel.asMap().clear();
        productType.persist();
        ProductGroup productGroup = productType.getProductGroup();
        ProductGroup productGroup2 = ProductGroup.findProductGroup(productGroup.getId());
        Set<ProductType> types = productGroup2.getTypes();
        boolean productTypeExist = false ;
        for (ProductType productType2 : types) {
			if(productType2.equals(productType)){
				productTypeExist = true;
				break ;
			}
		}
        if(!productTypeExist){
        	productGroup2.getTypes().add(productType);
        	productGroup2.merge().flush();
        }
        return "redirect:/producttypes/" + encodeUrlPathSegment(productType.getId().toString(), httpServletRequest);
    }
}
