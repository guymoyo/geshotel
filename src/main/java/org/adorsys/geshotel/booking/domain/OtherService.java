package org.adorsys.geshotel.booking.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;

@RooJavaBean
@RooToString
@RooEntity
public class OtherService {

    private String name;

    @NotNull
    private BigDecimal amount;

    private String serviceDesc;
}
