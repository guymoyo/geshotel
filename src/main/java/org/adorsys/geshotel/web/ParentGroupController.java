package org.adorsys.geshotel.web;

import org.adorsys.geshotel.resto.domain.ParentGroup;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "parentgroups", formBackingObject = ParentGroup.class)
@RequestMapping("/parentgroups")
@Controller
public class ParentGroupController {
}
