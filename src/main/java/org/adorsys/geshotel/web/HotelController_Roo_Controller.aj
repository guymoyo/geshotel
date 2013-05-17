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
import org.adorsys.geshotel.domain.Hotel;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect HotelController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST)
    public String HotelController.create(@Valid Hotel hotel, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("hotel", hotel);
            return "hotels/create";
        }
        uiModel.asMap().clear();
        hotel.persist();
        return "redirect:/hotels/" + encodeUrlPathSegment(hotel.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String HotelController.createForm(Model uiModel) {
        uiModel.addAttribute("hotel", new Hotel());
        return "hotels/create";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String HotelController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("hotel", Hotel.findHotel(id));
        uiModel.addAttribute("itemId", id);
        return "hotels/show";
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String HotelController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("hotels", Hotel.findHotelEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Hotel.countHotels() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("hotels", Hotel.findAllHotels());
        }
        return "hotels/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String HotelController.update(@Valid Hotel hotel, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("hotel", hotel);
            return "hotels/update";
        }
        uiModel.asMap().clear();
        hotel.merge();
        return "redirect:/hotels/" + encodeUrlPathSegment(hotel.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String HotelController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("hotel", Hotel.findHotel(id));
        return "hotels/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String HotelController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Hotel.findHotel(id).remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/hotels";
    }
    
    @ModelAttribute("hotels")
    public Collection<Hotel> HotelController.populateHotels() {
        return Hotel.findAllHotels();
    }
    
    String HotelController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
