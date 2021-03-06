// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.web;

import java.io.UnsupportedEncodingException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.adorsys.geshotel.resto.domain.ParentGroup;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect ParentGroupController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST)
    public String ParentGroupController.create(@Valid ParentGroup parentGroup, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("parentGroup", parentGroup);
            return "parentgroups/create";
        }
        uiModel.asMap().clear();
        parentGroup.persist();
        return "redirect:/parentgroups/" + encodeUrlPathSegment(parentGroup.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String ParentGroupController.createForm(Model uiModel) {
        uiModel.addAttribute("parentGroup", new ParentGroup());
        return "parentgroups/create";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String ParentGroupController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("parentgroup", ParentGroup.findParentGroup(id));
        uiModel.addAttribute("itemId", id);
        return "parentgroups/show";
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String ParentGroupController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("parentgroups", ParentGroup.findParentGroupEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) ParentGroup.countParentGroups() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("parentgroups", ParentGroup.findAllParentGroups());
        }
        return "parentgroups/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String ParentGroupController.update(@Valid ParentGroup parentGroup, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("parentGroup", parentGroup);
            return "parentgroups/update";
        }
        uiModel.asMap().clear();
        parentGroup.merge();
        return "redirect:/parentgroups/" + encodeUrlPathSegment(parentGroup.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String ParentGroupController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("parentGroup", ParentGroup.findParentGroup(id));
        return "parentgroups/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String ParentGroupController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        ParentGroup.findParentGroup(id).remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/parentgroups";
    }
    
    @ModelAttribute("parentgroups")
    public Collection<ParentGroup> ParentGroupController.populateParentGroups() {
        return ParentGroup.findAllParentGroups();
    }
    
    String ParentGroupController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        }
        catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
    
}
