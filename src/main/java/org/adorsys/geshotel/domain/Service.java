package org.adorsys.geshotel.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import java.util.Set;

import org.adorsys.geshotel.resto.domain.PeriodConf;

import java.util.HashSet;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;

@RooJavaBean
@RooToString
@RooEntity
public class Service {

    @NotNull
    private String title;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<PeriodConf> periods = new HashSet<PeriodConf>();
    
    
}
