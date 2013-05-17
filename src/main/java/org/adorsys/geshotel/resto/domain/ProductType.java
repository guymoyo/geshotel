package org.adorsys.geshotel.resto.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;
import java.util.Date;
import java.util.List;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Set;
import org.adorsys.geshotel.domain.ext.IdGenerator;
import org.adorsys.geshotel.resto.domain.Discount;
import java.util.HashSet;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import org.adorsys.geshotel.resto.domain.BarRestauState;
import javax.persistence.Enumerated;
import org.adorsys.geshotel.resto.domain.ProductGroup;
import javax.persistence.ManyToOne;

@RooJavaBean
@RooToString
@RooEntity
public class ProductType {

    @Column(name = "productTypeKeyBk", unique = true)
    private String productTypeKey;

    @NotNull
    @Column(unique = true)
    private String title;

    private String description;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date dateOfStore;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Discount> discounts = new HashSet<Discount>();

    @Enumerated
    private BarRestauState productTypeState;

    @ManyToOne
    private ProductGroup productGroup;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((productTypeKey == null) ? 0 : productTypeKey.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ProductType other = (ProductType) obj;
        if (productTypeKey == null) {
            if (other.productTypeKey != null) return false;
        } else if (!productTypeKey.equals(other.productTypeKey)) return false;
        return true;
    }

    @PrePersist
    public void prePersist() {
        this.productTypeKey = IdGenerator.generateId();
        this.productTypeState = BarRestauState.OPENED;
    }

    public static List<ProductType> findAllProductTypes() {
        BarRestauState barRestauState = BarRestauState.DISABLED;
        TypedQuery<ProductType> typedQuery = entityManager().createQuery("SELECT o FROM ProductType o WHERE o.productTypeState != :barRestauState", ProductType.class);
        return typedQuery.setParameter("barRestauState", barRestauState).getResultList();
    }

    public String frontEndToString() {
        String representation = this.getTitle() + " : " + this.getDescription();
        return representation;
    }
}
