package org.adorsys.geshotel.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import java.math.BigDecimal;

@RooJavaBean
@RooToString
@RooEntity
public class Hotel {

    private String Name;

    private String Address;

    private String CodePostal;

    private String City;

    private String Email;

    private String PhoneNumber;

    private String Fax;

    private String WebPage;

    private BigDecimal TVA = BigDecimal.ZERO;
    
    private String logo;
}
