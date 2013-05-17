package org.adorsys.geshotel.resto.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.persistence.Column;
import org.adorsys.geshotel.resto.domain.KitchenOrder;
import javax.validation.constraints.NotNull;
import javax.persistence.ManyToOne;
import org.adorsys.geshotel.resto.domain.Product;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findKitchenOrderItemsByKitchenOrder" })
public class KitchenOrderItem {

    @Column(name = "kitchenOrderItemKeyBk", unique = true)
    private String kitchenOrderItemKey;

    @NotNull
    @ManyToOne
    private KitchenOrder kitchenOrder;

    @NotNull
    @ManyToOne
    private Product product;

    @NotNull
    private BigDecimal qty;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date dateOfOrdering;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((kitchenOrderItemKey == null) ? 0 : kitchenOrderItemKey.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        KitchenOrderItem other = (KitchenOrderItem) obj;
        if (kitchenOrderItemKey == null) {
            if (other.kitchenOrderItemKey != null) return false;
        } else if (!kitchenOrderItemKey.equals(other.kitchenOrderItemKey)) return false;
        return true;
    }
}
