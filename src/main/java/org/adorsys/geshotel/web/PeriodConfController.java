package org.adorsys.geshotel.web;

import org.adorsys.geshotel.resto.domain.PeriodConf;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "periodconfs", formBackingObject = PeriodConf.class)
@RequestMapping("/periodconfs")
@Controller
public class PeriodConfController {
}
