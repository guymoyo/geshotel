package org.adorsys.geshotel.resto.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.persistence.Column;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import org.springframework.format.annotation.DateTimeFormat;
import org.adorsys.geshotel.domain.UserAccount;
import javax.persistence.ManyToOne;
import org.adorsys.geshotel.resto.domain.BarRestauState;
import org.apache.james.mime4j.codec.EncoderUtil.Usage;
import org.hibernate.ejb.EntityManagerFactoryImpl;
import org.hibernate.ejb.criteria.CriteriaBuilderImpl;
import org.hibernate.ejb.criteria.CriteriaQueryImpl;

import javax.persistence.Enumerated;
import javax.persistence.criteria.CriteriaQuery;

import java.math.BigDecimal;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findCashRegistersByCashRegisterKeyEquals" })
public class CashRegister {

    @Column(name = "cashRegisterKeyBk", unique = true)
    private String cashRegisterKey;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date dateOfOpening;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date dateOfClosing;

    @NotNull
    @ManyToOne
    private UserAccount cashierAccount;

    @Enumerated
    private BarRestauState cashRegisterState;

    @NotNull
    private BigDecimal cashFund;

    public static TypedQuery<CashRegister> findUserCashRegister(BarRestauState cashRegisterState, UserAccount cashierAccount) {
        if (cashRegisterState == null) throw new IllegalArgumentException("The cashRegisterState argument is required");
        if (cashierAccount == null) throw new IllegalArgumentException("The cashierAccount argument is required");
        EntityManager em = CashRegister.entityManager();
        TypedQuery<CashRegister> q = em.createQuery("SELECT o FROM CashRegister AS o WHERE o.cashRegisterState = :cashRegisterState AND o.cashierAccount = :cashierAccount", CashRegister.class);
        q.setParameter("cashRegisterState", cashRegisterState);
        q.setParameter("cashierAccount", cashierAccount);
        return q;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cashRegisterKey == null) ? 0 : cashRegisterKey.hashCode());
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
		CashRegister other = (CashRegister) obj;
		if (cashRegisterKey == null) {
			if (other.cashRegisterKey != null)
				return false;
		} else if (!cashRegisterKey.equals(other.cashRegisterKey))
			return false;
		return true;
	}
    
	public static TypedQuery<CashRegister> findCashRegisterByPeriodForUser(Date findFrom,Date findTo,UserAccount cashierAccount){
		if(findFrom == null || findTo == null || cashierAccount == null ) throw new NullPointerException("Null paremeters aren't required");
		EntityManager em = CashRegister.entityManager();
		TypedQuery<CashRegister> typedQuery = em.createQuery("SELECT cr FROM CashRegister AS cr WHERE  :dateOfOpening <= cr.dateOfOpening AND cr.dateOfOpening <= :dateOfClosing" +
				" AND cr.cashierAccount = :cashierAccount", CashRegister.class);
		typedQuery.setParameter("dateOfOpening", findFrom);
		typedQuery.setParameter("dateOfClosing", findTo);
		typedQuery.setParameter("cashierAccount", cashierAccount);
		return typedQuery;
	}
	public static TypedQuery<CashRegister> findCashRegisterByPeriod(Date findFrom,Date findTo){
		if(findFrom == null || findTo == null  ) throw new NullPointerException("Null paremeters aren't required");
		EntityManager em = CashRegister.entityManager();
		TypedQuery<CashRegister> typedQuery = em.createQuery("SELECT cr FROM CashRegister AS cr WHERE  :dateOfOpening <= cr.dateOfOpening AND cr.dateOfOpening <= :dateOfClosing", CashRegister.class);
		typedQuery.setParameter("dateOfOpening", findFrom);
		typedQuery.setParameter("dateOfClosing", findTo);
		return typedQuery;
	}
	public static CashRegister findOpenedCashRegister(){
		BarRestauState opened = BarRestauState.OPENED;
		EntityManager em = CashRegister.entityManager();
		TypedQuery<CashRegister> typedQuery = em.createQuery("SELECT cr FROM CashRegister AS cr WHERE cr.cashRegisterState = :cashRegisterState", CashRegister.class);
		typedQuery.setParameter("cashRegisterState", opened);
		return typedQuery.getResultList().iterator().next();
	}	
}
