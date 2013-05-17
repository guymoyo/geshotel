package org.adorsys.geshotel.resto.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;

@RooJavaBean
@RooToString
@RooEntity
public class BrTable {

    @NotNull
    @Column(name = "brTableKeyBk", unique = true)
    private String brTableKey;

    @NotNull
    @Column(unique = true)
    private String tableNumber;

    private String description;
}
