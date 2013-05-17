package org.adorsys.geshotel.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adorsys.geshotel.domain.ext.IdGenerator;
import org.adorsys.geshotel.domain.security.SecurityUtil;
import org.adorsys.geshotel.resto.domain.BrTable;
import org.adorsys.geshotel.resto.domain.Inventory;
import org.adorsys.geshotel.resto.domain.ParentGroup;
import org.adorsys.geshotel.resto.domain.PeriodConf;
import org.adorsys.geshotel.resto.domain.Product;
import org.adorsys.geshotel.resto.domain.ProductGroup;
import org.adorsys.geshotel.resto.domain.ProductType;
import org.adorsys.geshotel.resto.domain.Supplier;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * use to init data.
 * 
 * @author adorsys
 * 
 */

@Service
public class DataInitializerHelper {
	public static final String DAY_SERVICE_NAME ="Service Du Jour";
	public static final String NIGHT_SERVICE_NAME= "Service Du Soir";
	public static final String DAY_NIGHT_SERVICE_NAME= "JOUR ET NUIT";
	
	@Transactional(rollbackFor=Exception.class)
	public  void initApplication() {
		//init period config
		newPeriodConf(DAY_SERVICE_NAME, new Date(), DateUtils.addYears(new Date(),50), 7, 15, "Service du jour, commence a 6h pour 15h").persist();
		newPeriodConf(NIGHT_SERVICE_NAME, new Date(), DateUtils.addYears(new Date(), 50), 15, 22, "Service du soir. Commence a 15 et termine a 22h").persist();
		newPeriodConf(DAY_NIGHT_SERVICE_NAME, new Date(), DateUtils.addYears(new Date(), 50), 15, 22, "Peut Travailler Pendant Les Deux Periodes.").persist();
		//init users
		newUserAccount("admin", "test123", "user admin", new Date(), getAdminRoleNames(), false, false,PeriodConf.findPeriodConfsByTitleEquals(DAY_NIGHT_SERVICE_NAME).getSingleResult()).persist();
		newUserAccount("manager", "test123", "user manager", new Date(), getManagerRoleNames(), false, false,PeriodConf.findPeriodConfsByTitleEquals(DAY_NIGHT_SERVICE_NAME).getSingleResult()).persist();
		newUserAccount("support", "test123", "user support", new Date(), getSupportRoleNames(), false, false,PeriodConf.findPeriodConfsByTitleEquals(DAY_NIGHT_SERVICE_NAME).getSingleResult()).persist();
		newUserAccount("reception", "test123", "user Reception", new Date(), getReceptionRoleNames(), false, false,PeriodConf.findPeriodConfsByTitleEquals(DAY_NIGHT_SERVICE_NAME).getSingleResult()).persist();
		newUserAccount("brmanager", "test123", "user bar resto", new Date(), getRestoManagerRoleNames(), false, false,PeriodConf.findPeriodConfsByTitleEquals(DAY_NIGHT_SERVICE_NAME).getSingleResult()).persist();
		newUserAccount("barman", "test123", "barman", new Date(), getBarmanRoleNames(), false, false, PeriodConf.findPeriodConfsByTitleEquals(DAY_NIGHT_SERVICE_NAME).getSingleResult()).persist();
		newUserAccount("cashier", "test123", "cashier", new Date(), getCashierRoleNames(), false, false, PeriodConf.findPeriodConfsByTitleEquals(DAY_NIGHT_SERVICE_NAME).getSingleResult()).persist();
		newUserAccount("kitchenmanager", "test123", "kitchenManager", new Date(), getKitchenManager(), false, false, PeriodConf.findPeriodConfsByTitleEquals(DAY_NIGHT_SERVICE_NAME).getSingleResult()).persist();
		//init parent groups
		newParentGroup(IdGenerator.generateId(), "N/A", "N/A", "Default Parent category").persist();
		//init products groups
		newProductGroup(IdGenerator.generateId(), "DEFAULT", "Default Group", null).persist();
//		newProductGroup(IdGenerator.generateId(), "BAR", "Produits du Bar", null).persist();
//		newProductGroup(IdGenerator.generateId(), "RESTO", "Products of the restaurant", null).persist();
		//init brTable
		newBrTable(IdGenerator.generateId(), "N/A", "Pour les ventes a l'emporte").persist();
		newBrTable(IdGenerator.generateId(), "TABLE-001", "Auto").persist();
		newBrTable(IdGenerator.generateId()	,"TABLE-002","Auto").persist();
		//init product type
//		newProductType(IdGenerator.generateId(), "BOISSONS", "Vins et alcools", new Date()).persist();
//		newProductType(IdGenerator.generateId(), "REPAS", "repas", new Date()).persist();
		//init services
		newService("BAR/RESTAU", new HashSet<PeriodConf>(PeriodConf.findAllPeriodConfs())).persist();
		newService("RECEPTION", null).persist();
		//init employees
		newEmployee("Guy Moyo", org.adorsys.geshotel.domain.Service.findService(new Long(1)),RandomStringUtils.randomAlphanumeric(6), Gender.Male, DateUtils.addYears(new Date(), -35)).persist();
		newEmployee("Boris waguia",org.adorsys.geshotel.domain.Service.findService(new Long(1)), RandomStringUtils.randomAlphanumeric(6), Gender.Male, DateUtils.addYears(new Date(), -45)).persist();
		//init suppliers
		newSupplier(IdGenerator.generateId(), "SUPP-0001", "BRASSERIES CAM", "+237 399393999", "DOUALA-NDOKOTTI", new Date()).persist();
		newSupplier(IdGenerator.generateId(), "SUPP-0002", "GUINESS CAM", "+237 3939283883", "DOUALA-ANGE-RAPHAEL", new Date()).persist();
	}
	
	public Set<RoleName> getBarmanRoleNames() {
		Set<RoleName> roles = new HashSet<RoleName>();
		roles.add(RoleName.ROLE_BARMAN);
		return roles;
	}
	public Set<RoleName> getKitchenManager(){
		 Set<RoleName> roles = new HashSet<RoleName>(2);
		 roles.add(RoleName.ROLE_KITCHEN_MANAGER);
		 return roles;
	}
	public Set<RoleName> getCashierRoleNames() {
		Set<RoleName> roles = new HashSet<RoleName>();
		roles.add(RoleName.ROLE_CASH_AGENT);
//		roles.add(RoleName.ROLE_SALE_AGENT);
		return roles;
	}

	public static  Set<RoleName> getAdminRoleNames(){
		Set<RoleName> roles = new HashSet<RoleName>();
		roles.add(RoleName.ROLE_ADMIN);
		return roles;
	}
	public static Set<RoleName> getManagerRoleNames(){
		Set<RoleName> roles = new HashSet<RoleName>();
		roles.add(RoleName.ROLE_MANAGER);
		return roles;
	}
	public static Set<RoleName> getSupportRoleNames(){
		Set<RoleName> roles = new HashSet<RoleName>();
		roles.add(RoleName.ROLE_MANAGER);
		roles.add(RoleName.ROLE_CASH_AGENT);
		return roles;
	}
	public static Set<RoleName> getReceptionRoleNames(){
		Set<RoleName> roles = new HashSet<RoleName>();
		roles.add(RoleName.ROLE_RECEPTION);
		return roles;
	}
	public static Set<RoleName> getRestoManagerRoleNames(){
		Set<RoleName> roles = new HashSet<RoleName>();
		roles.add(RoleName.ROLE_RESTO_MANAGER);
		return roles;
	}
	
    public static Set<RoleName> getBarRestoRolesName() {
        Set<RoleName> barRoles = new HashSet<RoleName>();
        barRoles.add(RoleName.ROLE_RESTO_MANAGER);
        barRoles.add(RoleName.ROLE_SALE_AGENT);
        barRoles.add(RoleName.ROLE_CASH_AGENT);
        barRoles.add(RoleName.ROLE_BARMAN);
        return barRoles;
    }

    public static Set<RoleName> getBookingRolesName() {
        Set<RoleName> restoNames = new HashSet<RoleName>();
        restoNames.add(RoleName.ROLE_RECEPTION);
        restoNames.add(RoleName.ROLE_CASH_AGENT);
        return restoNames;
    }
	
	public  UserAccount newUserAccount(String identifier, String password,
			String lastName, Date entryDate, Set<RoleName> roleNames,
			boolean disableLogin, boolean accountLocked,PeriodConf period) {
		UserAccount userAccount = new UserAccount();
		userAccount.setAccountLocked(accountLocked);
		userAccount.setDisableLogin(disableLogin);
		userAccount.setEntryDate(entryDate);
		userAccount.setIdentifier(identifier);
		userAccount.setLastName(lastName);
		userAccount.setRoleNames(roleNames);
		userAccount.setPassword(password);
		userAccount.setPeriodConf(period);
		return userAccount;
	}
	public  ParentGroup newParentGroup(String parentGroupKey, String productGroupKey,
			String productGroupParentKey, String description) {
		ParentGroup parentGroup = new ParentGroup();
		parentGroup.setDescription(description);
		parentGroup.setParentGroupKey(parentGroupKey);
		parentGroup.setProductGroupKey(productGroupKey);
		parentGroup.setProductGroupParentKey(productGroupParentKey);
		return parentGroup;
	}
	public  ProductGroup newProductGroup(String productGroupKey, String title,
			String description, Set<ProductType> types) {
		ProductGroup productGroup = new ProductGroup();
		productGroup.setDescription(description);
		productGroup.setProductGroupKey(productGroupKey);
		productGroup.setTitle(title);
		productGroup.setTypes(types);
		return productGroup;
	}
	public  BrTable newBrTable(String brTableKey, String tableNumber, String description) {
		BrTable brTable = new BrTable();
		brTable.setBrTableKey(brTableKey);
		brTable.setDescription(description);
		brTable.setTableNumber(tableNumber);
		return brTable;
	}
	public  ProductType newProductType(String productTypeKey, String title, String description,
			Date dateOfStore) {
		ProductType productType = new ProductType();
		productType.setDateOfStore(dateOfStore);
		productType.setDescription(description);
		productType.setProductTypeKey(productTypeKey);
		productType.setTitle(title);
		return productType;
	}
	public  Customer newCustomer(String firstName, String lastName, Gender gender,
			String identityNumber, Date bornDate) {
		Customer customer = new Customer();
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setGender(gender);
		customer.setIdentityNumber(identityNumber);
		customer.setBornDate(bornDate);
		return customer;
	}

	public PeriodConf newPeriodConf(String title, Date validFrom, Date validTo,
			int openingTime, int closingTime, String description) {
		PeriodConf periodConf = new PeriodConf();
		periodConf.setTitle(title);
		periodConf.setDescription(description);
		periodConf.setOpeningTime(openingTime);
		periodConf.setValidFrom(validFrom);
		periodConf.setValidTo(validTo);
		periodConf.setClosingTime(closingTime);
		return periodConf;
	}
	
	public Employee newEmployee(String fullName, org.adorsys.geshotel.domain.Service service, String identifier, Gender gender,
			Date bornDate) {
		Employee employee = new Employee();
		employee.setBornDate(bornDate);
		employee.setFullName(fullName);
		employee.setGender(gender);
		employee.setIdentifier(identifier);
		employee.setService(service);
		return employee;
	}
	public org.adorsys.geshotel.domain.Service  newService(String title, Set<PeriodConf> periods) {
		
		org.adorsys.geshotel.domain.Service service = new org.adorsys.geshotel.domain.Service();
		service.setTitle(title);
		service.setPeriods(periods);
		return service;
	}
	public void initFirstInventory(){
		Inventory inventory = new Inventory();
		inventory.setInventoryDate(new Date());
		inventory.setIsClosed(Boolean.FALSE);
		inventory.setPeriodConf(PeriodConf.findPeriodConfsByTitleEquals(DAY_NIGHT_SERVICE_NAME).getResultList().iterator().next());
		inventory.setTitle("Premier Inventaire Therique Du Systeme");
		inventory.setUserAccount(SecurityUtil.getBaseUser());
		inventory.persist();
		inventory.flush();
	}
	public void initInventoryItems(){
		List<Product> allProducts = Product.findAllProducts();
		for (Product product : allProducts) {
		}
	}
	public Supplier newSupplier(String supplierKey, String identifier, String designation,
			String phoneNumber, String address, Date dateOfCreation) {
		Supplier supplier = new Supplier();
		supplier.setAddress(address);
		supplier.setDateOfCreation(dateOfCreation);
		supplier.setDesignation(designation);
		supplier.setIdentifier(identifier);
		supplier.setPhoneNumber(phoneNumber);
		supplier.setSupplierKey(supplierKey);
		return supplier;
	}
}

