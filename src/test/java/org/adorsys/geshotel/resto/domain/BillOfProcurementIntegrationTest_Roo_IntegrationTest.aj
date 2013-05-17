// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.resto.domain;

import org.adorsys.geshotel.resto.domain.BillOfProcurementDataOnDemand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect BillOfProcurementIntegrationTest_Roo_IntegrationTest {
    
    declare @type: BillOfProcurementIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: BillOfProcurementIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml");
    
    declare @type: BillOfProcurementIntegrationTest: @Transactional;
    
    @Autowired
    private BillOfProcurementDataOnDemand BillOfProcurementIntegrationTest.dod;
    
    @Test
    public void BillOfProcurementIntegrationTest.testCountBillOfProcurements() {
        org.junit.Assert.assertNotNull("Data on demand for 'BillOfProcurement' failed to initialize correctly", dod.getRandomBillOfProcurement());
        long count = org.adorsys.geshotel.resto.domain.BillOfProcurement.countBillOfProcurements();
        org.junit.Assert.assertTrue("Counter for 'BillOfProcurement' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void BillOfProcurementIntegrationTest.testFindBillOfProcurement() {
        org.adorsys.geshotel.resto.domain.BillOfProcurement obj = dod.getRandomBillOfProcurement();
        org.junit.Assert.assertNotNull("Data on demand for 'BillOfProcurement' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'BillOfProcurement' failed to provide an identifier", id);
        obj = org.adorsys.geshotel.resto.domain.BillOfProcurement.findBillOfProcurement(id);
        org.junit.Assert.assertNotNull("Find method for 'BillOfProcurement' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'BillOfProcurement' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void BillOfProcurementIntegrationTest.testFindAllBillOfProcurements() {
        org.junit.Assert.assertNotNull("Data on demand for 'BillOfProcurement' failed to initialize correctly", dod.getRandomBillOfProcurement());
        long count = org.adorsys.geshotel.resto.domain.BillOfProcurement.countBillOfProcurements();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'BillOfProcurement', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<org.adorsys.geshotel.resto.domain.BillOfProcurement> result = org.adorsys.geshotel.resto.domain.BillOfProcurement.findAllBillOfProcurements();
        org.junit.Assert.assertNotNull("Find all method for 'BillOfProcurement' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'BillOfProcurement' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void BillOfProcurementIntegrationTest.testFindBillOfProcurementEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'BillOfProcurement' failed to initialize correctly", dod.getRandomBillOfProcurement());
        long count = org.adorsys.geshotel.resto.domain.BillOfProcurement.countBillOfProcurements();
        if (count > 20) count = 20;
        java.util.List<org.adorsys.geshotel.resto.domain.BillOfProcurement> result = org.adorsys.geshotel.resto.domain.BillOfProcurement.findBillOfProcurementEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'BillOfProcurement' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'BillOfProcurement' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void BillOfProcurementIntegrationTest.testFlush() {
        org.adorsys.geshotel.resto.domain.BillOfProcurement obj = dod.getRandomBillOfProcurement();
        org.junit.Assert.assertNotNull("Data on demand for 'BillOfProcurement' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'BillOfProcurement' failed to provide an identifier", id);
        obj = org.adorsys.geshotel.resto.domain.BillOfProcurement.findBillOfProcurement(id);
        org.junit.Assert.assertNotNull("Find method for 'BillOfProcurement' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyBillOfProcurement(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'BillOfProcurement' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void BillOfProcurementIntegrationTest.testMerge() {
        org.adorsys.geshotel.resto.domain.BillOfProcurement obj = dod.getRandomBillOfProcurement();
        org.junit.Assert.assertNotNull("Data on demand for 'BillOfProcurement' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'BillOfProcurement' failed to provide an identifier", id);
        obj = org.adorsys.geshotel.resto.domain.BillOfProcurement.findBillOfProcurement(id);
        boolean modified =  dod.modifyBillOfProcurement(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        org.adorsys.geshotel.resto.domain.BillOfProcurement merged =  obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'BillOfProcurement' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void BillOfProcurementIntegrationTest.testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'BillOfProcurement' failed to initialize correctly", dod.getRandomBillOfProcurement());
        org.adorsys.geshotel.resto.domain.BillOfProcurement obj = dod.getNewTransientBillOfProcurement(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'BillOfProcurement' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'BillOfProcurement' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'BillOfProcurement' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void BillOfProcurementIntegrationTest.testRemove() {
        org.adorsys.geshotel.resto.domain.BillOfProcurement obj = dod.getRandomBillOfProcurement();
        org.junit.Assert.assertNotNull("Data on demand for 'BillOfProcurement' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'BillOfProcurement' failed to provide an identifier", id);
        obj = org.adorsys.geshotel.resto.domain.BillOfProcurement.findBillOfProcurement(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'BillOfProcurement' with identifier '" + id + "'", org.adorsys.geshotel.resto.domain.BillOfProcurement.findBillOfProcurement(id));
    }
    
}