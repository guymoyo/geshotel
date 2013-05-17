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
import org.adorsys.geshotel.resto.domain.PeriodConf;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect PeriodConfController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST)
    public String PeriodConfController.create(@Valid PeriodConf periodConf, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("periodConf", periodConf);
            addDateTimeFormatPatterns(uiModel);
            return "periodconfs/create";
        }
        uiModel.asMap().clear();
        periodConf.persist();
        return "redirect:/periodconfs/" + encodeUrlPathSegment(periodConf.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String PeriodConfController.createForm(Model uiModel) {
        uiModel.addAttribute("periodConf", new PeriodConf());
        addDateTimeFormatPatterns(uiModel);
        return "periodconfs/create";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String PeriodConfController.show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("periodconf", PeriodConf.findPeriodConf(id));
        uiModel.addAttribute("itemId", id);
        return "periodconfs/show";
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String PeriodConfController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("periodconfs", PeriodConf.findPeriodConfEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) PeriodConf.countPeriodConfs() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("periodconfs", PeriodConf.findAllPeriodConfs());
        }
        addDateTimeFormatPatterns(uiModel);
        return "periodconfs/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String PeriodConfController.update(@Valid PeriodConf periodConf, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("periodConf", periodConf);
            addDateTimeFormatPatterns(uiModel);
            return "periodconfs/update";
        }
        uiModel.asMap().clear();
        periodConf.merge();
        return "redirect:/periodconfs/" + encodeUrlPathSegment(periodConf.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String PeriodConfController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("periodConf", PeriodConf.findPeriodConf(id));
        addDateTimeFormatPatterns(uiModel);
        return "periodconfs/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String PeriodConfController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        PeriodConf.findPeriodConf(id).remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/periodconfs";
    }
    
    @ModelAttribute("periodconfs")
    public Collection<PeriodConf> PeriodConfController.populatePeriodConfs() {
        return PeriodConf.findAllPeriodConfs();
    }
    
    void PeriodConfController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("periodConf_validfrom_date_format", "HH:mm");
        uiModel.addAttribute("periodConf_validto_date_format", "HH:mm");
    }
    
    String PeriodConfController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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