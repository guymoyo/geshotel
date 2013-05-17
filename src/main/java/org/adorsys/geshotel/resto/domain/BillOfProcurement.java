package org.adorsys.geshotel.resto.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;
import java.util.Set;
import org.adorsys.geshotel.resto.domain.SaleItem;
import java.util.HashSet;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

@RooJavaBean
@RooToString
@RooEntity
public class BillOfProcurement {

    @NotNull
    @Column(name = "sellKeyBk", unique = true)
    private String sellKey;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<SaleItem> saleItems = new HashSet<SaleItem>();

    private BigDecimal totalAmount;

    private BigDecimal tax;

    private BigDecimal discount;

    private BigDecimal amountDue;

    private String note;

    private String restaurantName;

    private String broker;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date dateOfProcurement;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sellKey == null) ? 0 : sellKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BillOfProcurement other = (BillOfProcurement) obj;
		if (sellKey == null) {
			if (other.sellKey != null)
				return false;
		} else if (!sellKey.equals(other.sellKey))
			return false;
		return true;
	}
    
    
}
