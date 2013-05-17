package org.adorsys.geshotel.web;

import org.adorsys.geshotel.booking.domain.OtherService;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "otherservices", formBackingObject = OtherService.class)
@RequestMapping("/otherservices")
@Controller
public class OtherServiceController {
}
