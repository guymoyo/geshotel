package org.adorsys.geshotel.booking.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;

@RooJavaBean
@RooToString
@RooEntity
public class RoomCategory {

    private String title;

    private String description;

    @NotNull
    private BigDecimal price;

    @NotNull
    private BigDecimal priceMin;
}