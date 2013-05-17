package org.adorsys.geshotel.web;

import org.adorsys.geshotel.domain.Service;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "services", formBackingObject = Service.class)
@RequestMapping("/services")
@Controller
public class ServiceController {
}
