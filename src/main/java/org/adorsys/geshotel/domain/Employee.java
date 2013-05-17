package org.adorsys.geshotel.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;
import org.adorsys.geshotel.domain.Service;
import javax.persistence.ManyToOne;
import org.adorsys.geshotel.domain.Gender;
import javax.persistence.Enumerated;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import org.adorsys.geshotel.resto.domain.PeriodConf;

@RooJavaBean
@RooToString
@RooEntity
public class Employee {

    @NotNull
    private String fullName;

    @NotNull
    @Column(unique = true)
    private String identifier;

    @NotNull
    @ManyToOne
    private Service service;

    @Enumerated
    private Gender gender;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date bornDate;    
    
}
