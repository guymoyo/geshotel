package org.adorsys.geshotel.resto.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

@RooJavaBean
@RooToString
@RooEntity
public class Supplier {

    @Column(name = "supplierKeyBk", unique = true)
    private String supplierKey;

    @NotNull
    @Column(unique = true)
    private String identifier;

    @NotNull
    private String designation;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String address;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date dateOfCreation;

}
