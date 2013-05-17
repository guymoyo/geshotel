package org.adorsys.geshotel.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.adorsys.geshotel.domain.ext.IdGenerator;
import org.adorsys.geshotel.resto.domain.ParentGroup;
import org.adorsys.geshotel.resto.domain.ProductGroup;
import org.adorsys.geshotel.resto.work.beans.ParentGroupBean;
import org.adorsys.geshotel.resto.work.exception.NoParentGroupFound;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RooWebScaffold(path = "productgroups", formBackingObject = ProductGroup.class)
@RequestMapping("/productgroups")
@Controller
public class ProductGroupController {
	
	@RequestMapping(method = RequestMethod.POST)
    public String create(@Valid ProductGroup productGroup, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("productGroup", productGroup);
            return "productgroups/create";
        }
        uiModel.asMap().clear();
        productGroup.persist();
//        return "redirect:/productgroups/" + encodeUrlPathSegment(productGroup.getId().toString(), httpServletRequest);
        return "redirect:/productgroups/parentgroups?form&id="+encodeUrlPathSegment(productGroup.getId().toString(), httpServletRequest);
    }

    @RequestMapping(params = {"form","id"}, method = RequestMethod.GET,value="/parentgroups")
    public String createParentGroupForm(@RequestParam("id")Long productGroupId,Model uiModel) {
        ParentGroupBean parentGroupBean = createParentGroupBean(productGroupId);
        uiModel.addAttribute("parentGroupBean", parentGroupBean);
        return "productgroups/parentgroups/create";
    }

	private ParentGroupBean createParentGroupBean(Long productGroupId) {
		ParentGroupBean parentGroupBean = new ParentGroupBean();
        ProductGroup productGroup = ProductGroup.findProductGroup(productGroupId);
        parentGroupBean.setProductGroupKey(productGroup.getProductGroupKey());
        List<String> allGroupKeys = new ArrayList<String>();
        List<ProductGroup> allProductGroups = ProductGroup.findAllProductGroups();
        Assert.notNull(allProductGroups, "No Product groups founds. Unable to mount the list of product groups keys "+this.getClass().getCanonicalName());
        for (ProductGroup group : allProductGroups) {
        	if(!group.getProductGroupKey().equals(productGroup.getProductGroupKey()))
        		allGroupKeys.add(group.getTitle());
        }
        parentGroupBean.setAllProductGroupKeys(allGroupKeys);
        parentGroupBean.setParentGroupKey(IdGenerator.generateId());
        parentGroupBean.setProductGroupKey(productGroup.getProductGroupKey());
		return parentGroupBean;
	}
    
    @RequestMapping(method = RequestMethod.POST,value="/parentgroups")
    public String createParentGroup(@Valid ParentGroupBean parentGroupBean, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
    	if (bindingResult.hasErrors()) {
            uiModel.addAttribute("parentGroupBean", parentGroupBean);
            return "productgroups/parentgroups/create";
        }
        uiModel.asMap().clear();
        ParentGroup parentGroup = parentGroupBean.newProductGroup();
        parentGroup.persist();
        return "redirect:/productgroups/parentgroups/" + parentGroup.getId().toString()+"/"+parentGroup.getProductGroupKey();
    }
    
    @RequestMapping(value = "/parentgroups/{id}/{groupId}", method = RequestMethod.GET)
    public String showParentGroup(@PathVariable("id") Long id,@PathVariable("groupId") String groupId, Model uiModel) {
        ProductGroup productGroup = ProductGroup.findProductGroupsByProductGroupKeyEquals(groupId).getSingleResult();
    	uiModel.addAttribute("parentgroup", ParentGroup.findParentGroup(id));
    	uiModel.addAttribute("productgroup",productGroup);
    	try {
			uiModel.addAttribute("productgroupparent", productGroup.findParentGroup());
		} catch (NoParentGroupFound e) {
			
			uiModel.addAttribute("exception", e);
			uiModel.addAttribute("parentGroupBean", createParentGroupBean(productGroup.getId()));
			e.printStackTrace();
			return "productgroups/parentgroups/create";
		}
//    	uiModel.addAttribute("productgroups",productGroup.findAllSubCategories());
        uiModel.addAttribute("itemId", id);
        return "productgroups/parentgroups/show";
    }
}
