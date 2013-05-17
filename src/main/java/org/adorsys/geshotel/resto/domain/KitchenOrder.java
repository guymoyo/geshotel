package org.adorsys.geshotel.resto.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.adorsys.geshotel.domain.UserAccount;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findKitchenOrdersBySaleAndKitchenOrderItemState", "findKitchenOrdersBySale", "findKitchenOrdersByKitchenOrderKeyEquals", "findKitchenOrdersByKitchenOrderItemState" })
public class KitchenOrder {

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date dateOfOrdering;

    @ManyToOne
    private Sale sale;

    @Enumerated
    private BarRestauState kitchenOrderItemState;

    @ManyToOne
    private UserAccount sellerAccount;

    private String kitchenOrderPath;

    private Integer kitchenOrderNumber;

    @Column(name = "kitchenOrderKeyBk", unique = true)
    private String kitchenOrderKey;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((kitchenOrderKey == null) ? 0 : kitchenOrderKey.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        KitchenOrder other = (KitchenOrder) obj;
        if (kitchenOrderKey == null) {
            if (other.kitchenOrderKey != null) return false;
        } else if (!kitchenOrderKey.equals(other.kitchenOrderKey)) return false;
        return true;
    }
    public static TypedQuery<KitchenOrder> findKitchenOrdersByKitchenOrderItemState(BarRestauState kitchenOrderItemState) {
        if (kitchenOrderItemState == null) throw new IllegalArgumentException("The kitchenOrderItemState argument is required");
        EntityManager em = KitchenOrder.entityManager();
        TypedQuery<KitchenOrder> q = em.createQuery("SELECT o FROM KitchenOrder AS o WHERE o.kitchenOrderItemState = :kitchenOrderItemState ORDER BY o.dateOfOrdering DESC", KitchenOrder.class);
        q.setParameter("kitchenOrderItemState", kitchenOrderItemState);
        return q;
    }
}
