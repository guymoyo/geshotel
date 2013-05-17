// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.resto.domain;

import org.adorsys.geshotel.resto.domain.BarRestauInvoiceDataOnDemand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect BarRestauInvoiceIntegrationTest_Roo_IntegrationTest {
    
    declare @type: BarRestauInvoiceIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: BarRestauInvoiceIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml");
    
    declare @type: BarRestauInvoiceIntegrationTest: @Transactional;
    
    @Autowired
    private BarRestauInvoiceDataOnDemand BarRestauInvoiceIntegrationTest.dod;
    
    @Test
    public void BarRestauInvoiceIntegrationTest.testCountBarRestauInvoices() {
        org.junit.Assert.assertNotNull("Data on demand for 'BarRestauInvoice' failed to initialize correctly", dod.getRandomBarRestauInvoice());
        long count = org.adorsys.geshotel.resto.domain.BarRestauInvoice.countBarRestauInvoices();
        org.junit.Assert.assertTrue("Counter for 'BarRestauInvoice' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void BarRestauInvoiceIntegrationTest.testFindBarRestauInvoice() {
        org.adorsys.geshotel.resto.domain.BarRestauInvoice obj = dod.getRandomBarRestauInvoice();
        org.junit.Assert.assertNotNull("Data on demand for 'BarRestauInvoice' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'BarRestauInvoice' failed to provide an identifier", id);
        obj = org.adorsys.geshotel.resto.domain.BarRestauInvoice.findBarRestauInvoice(id);
        org.junit.Assert.assertNotNull("Find method for 'BarRestauInvoice' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'BarRestauInvoice' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void BarRestauInvoiceIntegrationTest.testFindAllBarRestauInvoices() {
        org.junit.Assert.assertNotNull("Data on demand for 'BarRestauInvoice' failed to initialize correctly", dod.getRandomBarRestauInvoice());
        long count = org.adorsys.geshotel.resto.domain.BarRestauInvoice.countBarRestauInvoices();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'BarRestauInvoice', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<org.adorsys.geshotel.resto.domain.BarRestauInvoice> result = org.adorsys.geshotel.resto.domain.BarRestauInvoice.findAllBarRestauInvoices();
        org.junit.Assert.assertNotNull("Find all method for 'BarRestauInvoice' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'BarRestauInvoice' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void BarRestauInvoiceIntegrationTest.testFindBarRestauInvoiceEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'BarRestauInvoice' failed to initialize correctly", dod.getRandomBarRestauInvoice());
        long count = org.adorsys.geshotel.resto.domain.BarRestauInvoice.countBarRestauInvoices();
        if (count > 20) count = 20;
        java.util.List<org.adorsys.geshotel.resto.domain.BarRestauInvoice> result = org.adorsys.geshotel.resto.domain.BarRestauInvoice.findBarRestauInvoiceEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'BarRestauInvoice' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'BarRestauInvoice' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void BarRestauInvoiceIntegrationTest.testFlush() {
        org.adorsys.geshotel.resto.domain.BarRestauInvoice obj = dod.getRandomBarRestauInvoice();
        org.junit.Assert.assertNotNull("Data on demand for 'BarRestauInvoice' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'BarRestauInvoice' failed to provide an identifier", id);
        obj = org.adorsys.geshotel.resto.domain.BarRestauInvoice.findBarRestauInvoice(id);
        org.junit.Assert.assertNotNull("Find method for 'BarRestauInvoice' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyBarRestauInvoice(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'BarRestauInvoice' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void BarRestauInvoiceIntegrationTest.testMerge() {
        org.adorsys.geshotel.resto.domain.BarRestauInvoice obj = dod.getRandomBarRestauInvoice();
        org.junit.Assert.assertNotNull("Data on demand for 'BarRestauInvoice' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'BarRestauInvoice' failed to provide an identifier", id);
        obj = org.adorsys.geshotel.resto.domain.BarRestauInvoice.findBarRestauInvoice(id);
        boolean modified =  dod.modifyBarRestauInvoice(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        org.adorsys.geshotel.resto.domain.BarRestauInvoice merged =  obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'BarRestauInvoice' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void BarRestauInvoiceIntegrationTest.testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'BarRestauInvoice' failed to initialize correctly", dod.getRandomBarRestauInvoice());
        org.adorsys.geshotel.resto.domain.BarRestauInvoice obj = dod.getNewTransientBarRestauInvoice(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'BarRestauInvoice' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'BarRestauInvoice' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'BarRestauInvoice' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void BarRestauInvoiceIntegrationTest.testRemove() {
        org.adorsys.geshotel.resto.domain.BarRestauInvoice obj = dod.getRandomBarRestauInvoice();
        org.junit.Assert.assertNotNull("Data on demand for 'BarRestauInvoice' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'BarRestauInvoice' failed to provide an identifier", id);
        obj = org.adorsys.geshotel.resto.domain.BarRestauInvoice.findBarRestauInvoice(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'BarRestauInvoice' with identifier '" + id + "'", org.adorsys.geshotel.resto.domain.BarRestauInvoice.findBarRestauInvoice(id));
    }
    
}