// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.resto.domain;

import org.adorsys.geshotel.resto.domain.SaleDataOnDemand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect SaleIntegrationTest_Roo_IntegrationTest {
    
    declare @type: SaleIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: SaleIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml");
    
    declare @type: SaleIntegrationTest: @Transactional;
    
    @Autowired
    private SaleDataOnDemand SaleIntegrationTest.dod;
    
    @Test
    public void SaleIntegrationTest.testCountSales() {
        org.junit.Assert.assertNotNull("Data on demand for 'Sale' failed to initialize correctly", dod.getRandomSale());
        long count = org.adorsys.geshotel.resto.domain.Sale.countSales();
        org.junit.Assert.assertTrue("Counter for 'Sale' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void SaleIntegrationTest.testFindSale() {
        org.adorsys.geshotel.resto.domain.Sale obj = dod.getRandomSale();
        org.junit.Assert.assertNotNull("Data on demand for 'Sale' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Sale' failed to provide an identifier", id);
        obj = org.adorsys.geshotel.resto.domain.Sale.findSale(id);
        org.junit.Assert.assertNotNull("Find method for 'Sale' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'Sale' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void SaleIntegrationTest.testFindAllSales() {
        org.junit.Assert.assertNotNull("Data on demand for 'Sale' failed to initialize correctly", dod.getRandomSale());
        long count = org.adorsys.geshotel.resto.domain.Sale.countSales();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'Sale', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<org.adorsys.geshotel.resto.domain.Sale> result = org.adorsys.geshotel.resto.domain.Sale.findAllSales();
        org.junit.Assert.assertNotNull("Find all method for 'Sale' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'Sale' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void SaleIntegrationTest.testFindSaleEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'Sale' failed to initialize correctly", dod.getRandomSale());
        long count = org.adorsys.geshotel.resto.domain.Sale.countSales();
        if (count > 20) count = 20;
        java.util.List<org.adorsys.geshotel.resto.domain.Sale> result = org.adorsys.geshotel.resto.domain.Sale.findSaleEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'Sale' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'Sale' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void SaleIntegrationTest.testFlush() {
        org.adorsys.geshotel.resto.domain.Sale obj = dod.getRandomSale();
        org.junit.Assert.assertNotNull("Data on demand for 'Sale' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Sale' failed to provide an identifier", id);
        obj = org.adorsys.geshotel.resto.domain.Sale.findSale(id);
        org.junit.Assert.assertNotNull("Find method for 'Sale' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifySale(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'Sale' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void SaleIntegrationTest.testMerge() {
        org.adorsys.geshotel.resto.domain.Sale obj = dod.getRandomSale();
        org.junit.Assert.assertNotNull("Data on demand for 'Sale' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Sale' failed to provide an identifier", id);
        obj = org.adorsys.geshotel.resto.domain.Sale.findSale(id);
        boolean modified =  dod.modifySale(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        org.adorsys.geshotel.resto.domain.Sale merged =  obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'Sale' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void SaleIntegrationTest.testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'Sale' failed to initialize correctly", dod.getRandomSale());
        org.adorsys.geshotel.resto.domain.Sale obj = dod.getNewTransientSale(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'Sale' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'Sale' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'Sale' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void SaleIntegrationTest.testRemove() {
        org.adorsys.geshotel.resto.domain.Sale obj = dod.getRandomSale();
        org.junit.Assert.assertNotNull("Data on demand for 'Sale' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Sale' failed to provide an identifier", id);
        obj = org.adorsys.geshotel.resto.domain.Sale.findSale(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'Sale' with identifier '" + id + "'", org.adorsys.geshotel.resto.domain.Sale.findSale(id));
    }
    
}