package org.adorsys.geshotel.resto.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;
import org.adorsys.geshotel.domain.Customer;
import javax.persistence.ManyToOne;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

@RooJavaBean
@RooToString
@RooEntity
public class CustomerIdentifierHistory {

    @NotNull
    @Column(name = "historyKeyBk", unique = true)
    private String historyKey;

    @NotNull
    @ManyToOne
    private Customer customer;

    @NotNull
    @Column(unique = true)
    private String identifierValue;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date storingDate;
}
