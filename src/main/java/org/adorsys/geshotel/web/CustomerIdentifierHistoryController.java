package org.adorsys.geshotel.web;

import org.adorsys.geshotel.resto.domain.CustomerIdentifierHistory;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "customeridentifierhistorys", formBackingObject = CustomerIdentifierHistory.class)
@RequestMapping("/customeridentifierhistorys")
@Controller
public class CustomerIdentifierHistoryController {
}
