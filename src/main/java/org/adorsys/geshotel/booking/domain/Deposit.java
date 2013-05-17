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
@RooEntity(finders = { "findDepositsByOrganisation" })
public class Deposit {

    private String label;

    @NotNull
    private BigDecimal depositvalue;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date dateDeposit;


    @ManyToOne
    private UserAccount userAccount;
    
    @NotNull
    @ManyToOne
    private Customer customer;
    
    @PrePersist
    private void beforePersist(){
    	this.customer.flush();
    	this.customer.setAccount(this.customer.getAccount().add(this.getDepositvalue()));
    	this.customer.merge();
    }

	public Deposit(String label, BigDecimal depositvalue, Date dateDeposit,
			UserAccount userAccount, Customer customer) {
		super();
		this.label = label;
		this.depositvalue = depositvalue;
		this.dateDeposit = dateDeposit;
		this.userAccount = userAccount;
		this.customer = customer;
	}
    
    
}
