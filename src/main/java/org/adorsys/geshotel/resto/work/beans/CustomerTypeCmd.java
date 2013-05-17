package org.adorsys.geshotel.resto.work.beans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
@RooJavaBean
@RooToString
public class CustomerTypeCmd {
	private String customerType;
	private List<String> customerTypes = new ArrayList<String>();
	private String activeRoom;
	private String hostedCustomerIdentifier ;
	public CustomerTypeCmd() {
		this.customerTypes = new ArrayList<String>();
		customerTypes.add(SIMPLE_CUSTOMER);
		customerTypes.add(HOSTED_CUSTOMER);
		customerTypes.add(EMPLOYEE);
	}
	
	public static final String SIMPLE_CUSTOMER="Simple Customer";
	public static final String HOSTED_CUSTOMER ="Hosted Customer";
	public static final String EMPLOYEE = "Employee";
}
