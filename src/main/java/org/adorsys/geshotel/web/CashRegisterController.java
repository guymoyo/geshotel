package org.adorsys.geshotel.web;

import org.adorsys.geshotel.resto.domain.CashRegister;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "cashregisters", formBackingObject = CashRegister.class)
@RequestMapping("/cashregisters")
@Controller
public class CashRegisterController {
}
