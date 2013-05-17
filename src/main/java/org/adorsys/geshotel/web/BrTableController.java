package org.adorsys.geshotel.web;

import org.adorsys.geshotel.resto.domain.BrTable;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "brtables", formBackingObject = BrTable.class)
@RequestMapping("/brtables")
@Controller
public class BrTableController {
}
