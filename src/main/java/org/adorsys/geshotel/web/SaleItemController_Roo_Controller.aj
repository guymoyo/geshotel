// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.web;

import java.io.UnsupportedEncodingException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.adorsys.geshotel.resto.domain.Product;
import org.adorsys.geshotel.resto.domain.Sale;
import org.adorsys.geshotel.resto.domain.SaleItem;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect SaleItemController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST)
    public String SaleItemController.create(@Valid SaleItem saleItem, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("saleItem", saleItem);
            addDateTimeFormatPatterns(uiModel);
            return "saleitems/create";
        }
        uiModel.asMap().clear();
        saleItem.persist();
        return "redirect:/saleitems/" + encodeUrlPathSegment(saleItem.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String SaleItemController.createForm(Model uiModel) {
        uiModel.addAttribute("saleItem", new SaleItem());
        addDateTimeFormatPatterns(uiModel);
        List dependencies = new ArrayList();
        if (Product.countProducts() == 0) {
            dependencies.add(new String[]{"product", "products"});
        }
        uiModel.addAttribute("dependencies", dependencies);
        return "saleitems/create";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String SaleItemController.show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("saleitem", SaleItem.findSaleItem(id));
        uiModel.addAttribute("itemId", id);
        return "saleitems/show";
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String SaleItemController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("saleitems", SaleItem.findSaleItemEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) SaleItem.countSaleItems() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("saleitems", SaleItem.findAllSaleItems());
        }
        addDateTimeFormatPatterns(uiModel);
        return "saleitems/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String SaleItemController.update(@Valid SaleItem saleItem, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("saleItem", saleItem);
            addDateTimeFormatPatterns(uiModel);
            return "saleitems/update";
        }
        uiModel.asMap().clear();
        saleItem.merge();
        return "redirect:/saleitems/" + encodeUrlPathSegment(saleItem.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String SaleItemController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("saleItem", SaleItem.findSaleItem(id));
        addDateTimeFormatPatterns(uiModel);
        return "saleitems/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String SaleItemController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        SaleItem.findSaleItem(id).remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/saleitems";
    }
    
    @ModelAttribute("products")
    public Collection<Product> SaleItemController.populateProducts() {
        return Product.findAllProducts();
    }
    
    @ModelAttribute("sales")
    public Collection<Sale> SaleItemController.populateSales() {
        return Sale.findAllSales();
    }
    
    @ModelAttribute("saleitems")
    public Collection<SaleItem> SaleItemController.populateSaleItems() {
        return SaleItem.findAllSaleItems();
    }
    
    void SaleItemController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("saleItem_dateofsale_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }
    
    String SaleItemController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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