package org.adorsys.geshotel.test;

import static org.junit.Assert.fail;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.adorsys.geshotel.domain.CustomerDataOnDemand;
import org.adorsys.geshotel.domain.DataInitializerHelper;
import org.adorsys.geshotel.domain.EmployeeDataOnDemand;
import org.adorsys.geshotel.resto.domain.InventoryDataOnDemand;
import org.adorsys.geshotel.resto.domain.InventoryItemDataOnDemand;
import org.adorsys.geshotel.resto.domain.ParentGroupDataOnDemand;
import org.adorsys.geshotel.resto.domain.ProductDataOnDemand;
import org.adorsys.geshotel.resto.domain.ProductGroupDataOnDemand;
import org.adorsys.geshotel.resto.domain.ProductTypeDataOnDemand;
import org.adorsys.geshotel.resto.domain.SaleDataOnDemand;
import org.adorsys.geshotel.resto.domain.SaleItemDataOnDemand;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(value=SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:META-INF/spring/applicationContext*.xml"})
@Transactional
public class TestInventoryProcess {
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private SaleDataOnDemand saleDataOnDemand;
	@Autowired
	private SaleItemDataOnDemand saleItemDataOnDemand;
	@Autowired
	private InventoryItemDataOnDemand inventoryItemDataOnDemand;
	@Autowired
	private InventoryDataOnDemand inventoryDataOnDemand;
	@Autowired
	private ProductDataOnDemand productDataOnDemand;
	@Autowired
	private ProductTypeDataOnDemand productTypeDataOnDemand;
	@Autowired
	private ProductGroupDataOnDemand productGroupDataOnDemand;
	@Autowired
	private ParentGroupDataOnDemand parentGroupDataOnDemand;
	@Autowired
	private EmployeeDataOnDemand employeeDataOnDemand;
	@Autowired
	private CustomerDataOnDemand customerDataOnDemand;
	@Autowired
	private DataInitializerHelper dataInitializerHelper;
	@Before
	public void before(){
		
	}
	
	@Test
	public void testGetInstance() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindInventoryFromSaleProcess() {
		fail("Not yet implemented");
	}

	@Test
	public void testNewInventory() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddFillInventoryFromCommand() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateInventoryItems() {
		fail("Not yet implemented");
	}

	@Test
	public void testCloseProcess() {
		fail("Not yet implemented");
	}

}
