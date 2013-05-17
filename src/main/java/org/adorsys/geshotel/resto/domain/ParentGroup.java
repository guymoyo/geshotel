package org.adorsys.geshotel.resto.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findParentGroupsByParentGroupKeyEquals", "findParentGroupsByProductGroupParentKeyEquals", "findParentGroupsByProductGroupKeyEquals" })
public class ParentGroup {

    @NotNull
    @Column(name = "parentGroupKeyBk", unique = true)
    private String parentGroupKey;

    @NotNull
    @Column(unique = true)
    private String productGroupKey;

    @NotNull
    private String productGroupParentKey;

    private String description;
}
