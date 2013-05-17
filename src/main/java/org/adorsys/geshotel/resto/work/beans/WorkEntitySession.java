package org.adorsys.geshotel.resto.work.beans;

import java.util.Date;
import java.util.List;

import org.springframework.roo.addon.javabean.RooJavaBean;
@RooJavaBean
public class WorkEntitySession<T> {
	public static boolean isActive =  true;
	private List<T> entities;
	private String sessionId;
	private Date dateIn;
	private Date dateOut;
	private String user;
	
	public WorkEntitySession(String sessionId, Date dateIn, Date dateOut,
			String user) {
		super();
		this.sessionId = sessionId;
		this.dateIn = dateIn;
		this.dateOut = dateOut;
		this.user = user;
	}

	@Override
	public String toString() {
		return "WorkEntitySession [entities=" + entities + ", sessionId="
				+ sessionId + ", dateIn=" + dateIn + ", user=" + user + "]";
	}
	
}
