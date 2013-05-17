package org.adorsys.geshotel.web;

import org.adorsys.geshotel.resto.domain.Provider;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "providers", formBackingObject = Provider.class)
@RequestMapping("/providers")
@Controller
public class ProviderController {
}
