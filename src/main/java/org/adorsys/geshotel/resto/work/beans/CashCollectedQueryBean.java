package org.adorsys.geshotel.resto.work.beans;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class CashCollectedQueryBean {
	@NotNull
	@DateTimeFormat(pattern="dd-MM-yyyy HH:mm")
	private Date collectedFrom ;
	@NotNull
	@DateTimeFormat(pattern="dd-MM-yyyy HH:mm")
	private Date collectedTo;
}
