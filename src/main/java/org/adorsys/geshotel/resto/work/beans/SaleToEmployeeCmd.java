package org.adorsys.geshotel.resto.work.beans;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.adorsys.geshotel.domain.Employee;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
public class SaleToEmployeeCmd {
	private List<Employee> employees = new ArrayList<Employee>();
	private String saleKey ;
	@NotNull
	private Employee employee;
	
	
	public SaleToEmployeeCmd() {
//		super();
		this.employees = Employee.findAllEmployees();
		System.out.println("\n \n Employee : "+employees.toString());
	}

	public SaleToEmployeeCmd(List<Employee> employees, String saleKey) {
		super();
		this.employees = employees;
		this.saleKey = saleKey;
	}
	
	
}
