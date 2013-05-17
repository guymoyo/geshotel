package org.adorsys.geshotel.booking.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.geshotel.domain.Customer;
import org.adorsys.geshotel.domain.UserAccount;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findRetraitsByOrganization" })
public class Retrait {

    private String label;

    private BigDecimal retraitvalue;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date dateRetrait;

    @ManyToOne
    private UserAccount userAccount;
    
    @NotNull
    @ManyToOne
    private Customer customer;
    
    @PrePersist
    private void beforePersist(){
    	this.customer.flush();
    	this.customer.setAccount(this.customer.getAccount().add(this.retraitvalue.negate()));
    	this.customer.merge();
    }

	public Retrait(String label, BigDecimal retraitvalue, Date dateRetrait,
			UserAccount userAccount, Customer customer) {
		super();
		this.label = label;
		this.retraitvalue = retraitvalue;
		this.dateRetrait = dateRetrait;
		this.userAccount = userAccount;
		this.customer = customer;
	}
    
    
}
