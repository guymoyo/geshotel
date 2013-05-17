package org.adorsys.geshotel.web;

import org.adorsys.geshotel.booking.domain.RoomCategory;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "roomcategorys", formBackingObject = RoomCategory.class)
@RequestMapping("/roomcategorys")
@Controller
public class RoomCategoryController {
}
