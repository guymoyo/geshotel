package org.adorsys.geshotel.resto.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;
import org.adorsys.geshotel.domain.Customer;
import javax.persistence.ManyToOne;
import org.adorsys.geshotel.resto.domain.Product;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.springframework.format.annotation.DateTimeFormat;
import org.adorsys.geshotel.resto.domain.Procurement;
import flexjson.JSONSerializer;
import org.adorsys.geshotel.resto.domain.Supplier;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findProcurementItemsByProcurement", "findProcurementItemsByProcurementAndProduct" })
public class ProcurementItem {

    @Column(unique = true)
    private String procurementItemKey;

    @ManyToOne
    private Customer customer;

    @NotNull
    @ManyToOne
    private Product product;

    private BigDecimal qty;

    private BigDecimal unitPrice;

    private BigDecimal amount;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date dateOfProcurement;

    @ManyToOne
    private Procurement procurement;

    @NotNull
    @ManyToOne
    private Supplier supplier;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((procurementItemKey == null) ? 0 : procurementItemKey.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ProcurementItem other = (ProcurementItem) obj;
        if (procurementItemKey == null) {
            if (other.procurementItemKey != null) return false;
        } else if (!procurementItemKey.equals(other.procurementItemKey)) return false;
        return true;
    }

    public String toJson() {
        JSONSerializer jsonSerializer = new JSONSerializer();
        jsonSerializer = jsonSerializer.exclude("*.class");
        return jsonSerializer.serialize(this);
    }
    public static List<ProcurementItem> findCurrentProcurementItems(){
    	List<Procurement> resultList = Procurement.findOpenedProcurements();
    	if(resultList == null || resultList.isEmpty()) return null;
    	return findProcurementItemsByProcurement(resultList.iterator().next()).getResultList();
    }
}
