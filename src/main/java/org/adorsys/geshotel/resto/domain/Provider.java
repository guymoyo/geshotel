package org.adorsys.geshotel.resto.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;

@RooJavaBean
@RooToString
@RooEntity
public class Provider {

    @NotNull
    @Column(name = "customerKeyBk", unique = true)
    private String providerKey;

    private String fullName;

    @NotNull
    @Column(unique = true)
    private String identifier;
}
