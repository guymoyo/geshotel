package org.adorsys.geshotel.resto.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import org.adorsys.geshotel.domain.UserAccount;
import org.adorsys.geshotel.domain.security.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findProcurementsByIsLatestProcurementNot", "findProcurementsByProcurementState", "findProcurementsByUserAccountAndProcurementState", "findProcurementsByProcurementKeyEquals" })
public class Procurement {

    private static Logger logger = LoggerFactory.getLogger(Procurement.class);

    @NotNull
    @Column(name = "procurementKeyBk", unique = true)
    private String procurementKey;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date dateOfProcurement;

    private BigDecimal amount;

    private Boolean isLatestProcurement;

    @Enumerated
    private BarRestauState procurementState;

    @ManyToOne
    private UserAccount userAccount;

    public static TypedQuery<Procurement> findProcurementsByIsLatestProcurementNot(Boolean isLatestProcurement) {
        if (isLatestProcurement == null) throw new IllegalArgumentException("The isLatestProcurement argument is required");
        EntityManager em = Procurement.entityManager();
        TypedQuery<Procurement> q = em.createQuery("SELECT o FROM Procurement AS o WHERE o.isLatestProcurement IS NOT :isLatestProcurement", Procurement.class);
        q.setParameter("isLatestProcurement", isLatestProcurement);
        return q;
    }

    public static List<Procurement> findOpenedProcurements() {
        return findProcurementsByUserAccountAndProcurementState(SecurityUtil.getBaseUser(), BarRestauState.OPENED).getResultList();
    }

    public static Procurement findOpenedProcurement() {
        List<Procurement> findAllProcurements = findOpenedProcurements();
        if (findAllProcurements == null || findAllProcurements.isEmpty()) return null;
        return findAllProcurements.iterator().next();
    }

    public static Procurement newProcurement() {
        return null;
    }
}
