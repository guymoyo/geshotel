// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.resto.domain;

import org.adorsys.geshotel.resto.domain.CustomerIdentifierHistoryDataOnDemand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect CustomerIdentifierHistoryIntegrationTest_Roo_IntegrationTest {
    
    declare @type: CustomerIdentifierHistoryIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: CustomerIdentifierHistoryIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml");
    
    declare @type: CustomerIdentifierHistoryIntegrationTest: @Transactional;
    
    @Autowired
    private CustomerIdentifierHistoryDataOnDemand CustomerIdentifierHistoryIntegrationTest.dod;
    
    @Test
    public void CustomerIdentifierHistoryIntegrationTest.testCountCustomerIdentifierHistorys() {
        org.junit.Assert.assertNotNull("Data on demand for 'CustomerIdentifierHistory' failed to initialize correctly", dod.getRandomCustomerIdentifierHistory());
        long count = org.adorsys.geshotel.resto.domain.CustomerIdentifierHistory.countCustomerIdentifierHistorys();
        org.junit.Assert.assertTrue("Counter for 'CustomerIdentifierHistory' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void CustomerIdentifierHistoryIntegrationTest.testFindCustomerIdentifierHistory() {
        org.adorsys.geshotel.resto.domain.CustomerIdentifierHistory obj = dod.getRandomCustomerIdentifierHistory();
        org.junit.Assert.assertNotNull("Data on demand for 'CustomerIdentifierHistory' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'CustomerIdentifierHistory' failed to provide an identifier", id);
        obj = org.adorsys.geshotel.resto.domain.CustomerIdentifierHistory.findCustomerIdentifierHistory(id);
        org.junit.Assert.assertNotNull("Find method for 'CustomerIdentifierHistory' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'CustomerIdentifierHistory' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void CustomerIdentifierHistoryIntegrationTest.testFindAllCustomerIdentifierHistorys() {
        org.junit.Assert.assertNotNull("Data on demand for 'CustomerIdentifierHistory' failed to initialize correctly", dod.getRandomCustomerIdentifierHistory());
        long count = org.adorsys.geshotel.resto.domain.CustomerIdentifierHistory.countCustomerIdentifierHistorys();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'CustomerIdentifierHistory', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<org.adorsys.geshotel.resto.domain.CustomerIdentifierHistory> result = org.adorsys.geshotel.resto.domain.CustomerIdentifierHistory.findAllCustomerIdentifierHistorys();
        org.junit.Assert.assertNotNull("Find all method for 'CustomerIdentifierHistory' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'CustomerIdentifierHistory' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void CustomerIdentifierHistoryIntegrationTest.testFindCustomerIdentifierHistoryEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'CustomerIdentifierHistory' failed to initialize correctly", dod.getRandomCustomerIdentifierHistory());
        long count = org.adorsys.geshotel.resto.domain.CustomerIdentifierHistory.countCustomerIdentifierHistorys();
        if (count > 20) count = 20;
        java.util.List<org.adorsys.geshotel.resto.domain.CustomerIdentifierHistory> result = org.adorsys.geshotel.resto.domain.CustomerIdentifierHistory.findCustomerIdentifierHistoryEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'CustomerIdentifierHistory' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'CustomerIdentifierHistory' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void CustomerIdentifierHistoryIntegrationTest.testFlush() {
        org.adorsys.geshotel.resto.domain.CustomerIdentifierHistory obj = dod.getRandomCustomerIdentifierHistory();
        org.junit.Assert.assertNotNull("Data on demand for 'CustomerIdentifierHistory' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'CustomerIdentifierHistory' failed to provide an identifier", id);
        obj = org.adorsys.geshotel.resto.domain.CustomerIdentifierHistory.findCustomerIdentifierHistory(id);
        org.junit.Assert.assertNotNull("Find method for 'CustomerIdentifierHistory' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyCustomerIdentifierHistory(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'CustomerIdentifierHistory' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void CustomerIdentifierHistoryIntegrationTest.testMerge() {
        org.adorsys.geshotel.resto.domain.CustomerIdentifierHistory obj = dod.getRandomCustomerIdentifierHistory();
        org.junit.Assert.assertNotNull("Data on demand for 'CustomerIdentifierHistory' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'CustomerIdentifierHistory' failed to provide an identifier", id);
        obj = org.adorsys.geshotel.resto.domain.CustomerIdentifierHistory.findCustomerIdentifierHistory(id);
        boolean modified =  dod.modifyCustomerIdentifierHistory(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        org.adorsys.geshotel.resto.domain.CustomerIdentifierHistory merged =  obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'CustomerIdentifierHistory' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void CustomerIdentifierHistoryIntegrationTest.testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'CustomerIdentifierHistory' failed to initialize correctly", dod.getRandomCustomerIdentifierHistory());
        org.adorsys.geshotel.resto.domain.CustomerIdentifierHistory obj = dod.getNewTransientCustomerIdentifierHistory(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'CustomerIdentifierHistory' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'CustomerIdentifierHistory' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'CustomerIdentifierHistory' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void CustomerIdentifierHistoryIntegrationTest.testRemove() {
        org.adorsys.geshotel.resto.domain.CustomerIdentifierHistory obj = dod.getRandomCustomerIdentifierHistory();
        org.junit.Assert.assertNotNull("Data on demand for 'CustomerIdentifierHistory' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'CustomerIdentifierHistory' failed to provide an identifier", id);
        obj = org.adorsys.geshotel.resto.domain.CustomerIdentifierHistory.findCustomerIdentifierHistory(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'CustomerIdentifierHistory' with identifier '" + id + "'", org.adorsys.geshotel.resto.domain.CustomerIdentifierHistory.findCustomerIdentifierHistory(id));
    }
    
}
