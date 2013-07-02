package org.adorsys.geshotel.booking.domain;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;


@RooJavaBean
public class Periode {
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd-MM-yyyy")
	private Date minDate;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd-MM-yyyy")
	private Date maxDate;

}
