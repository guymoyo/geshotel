package org.adorsys.geshotel.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.adorsys.geshotel.booking.domain.Room;
import org.adorsys.geshotel.utility.ErrorMessage;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RooWebScaffold(path = "rooms", formBackingObject = Room.class)
@RequestMapping("/rooms")
@Controller
public class RoomController {
	
	  @RequestMapping(method = RequestMethod.POST)
	    public String create(@Valid Room room, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
	        if (bindingResult.hasErrors() || room.isCreated()) {
	            uiModel.addAttribute("room", room);
	            uiModel.addAttribute("error", new ErrorMessage("Chambre deja cree avec ce numero", "room ready created with this number").getMessage());
	            return "rooms/create";
	        }
	        uiModel.asMap().clear();
	        room.persist();
	        return "redirect:/rooms/" + encodeUrlPathSegment(room.getId().toString(), httpServletRequest);
	    }
}
