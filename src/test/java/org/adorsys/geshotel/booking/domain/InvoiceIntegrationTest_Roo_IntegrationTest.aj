// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.booking.domain;

import org.adorsys.geshotel.booking.domain.InvoiceDataOnDemand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect InvoiceIntegrationTest_Roo_IntegrationTest {
    
    declare @type: InvoiceIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: InvoiceIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml");
    
    declare @type: InvoiceIntegrationTest: @Transactional;
    
    @Autowired
    private InvoiceDataOnDemand InvoiceIntegrationTest.dod;
    
    @Test
    public void InvoiceIntegrationTest.testCountInvoices() {
        org.junit.Assert.assertNotNull("Data on demand for 'Invoice' failed to initialize correctly", dod.getRandomInvoice());
        long count = org.adorsys.geshotel.booking.domain.Invoice.countInvoices();
        org.junit.Assert.assertTrue("Counter for 'Invoice' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void InvoiceIntegrationTest.testFindInvoice() {
        org.adorsys.geshotel.booking.domain.Invoice obj = dod.getRandomInvoice();
        org.junit.Assert.assertNotNull("Data on demand for 'Invoice' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Invoice' failed to provide an identifier", id);
        obj = org.adorsys.geshotel.booking.domain.Invoice.findInvoice(id);
        org.junit.Assert.assertNotNull("Find method for 'Invoice' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'Invoice' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void InvoiceIntegrationTest.testFindAllInvoices() {
        org.junit.Assert.assertNotNull("Data on demand for 'Invoice' failed to initialize correctly", dod.getRandomInvoice());
        long count = org.adorsys.geshotel.booking.domain.Invoice.countInvoices();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'Invoice', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<org.adorsys.geshotel.booking.domain.Invoice> result = org.adorsys.geshotel.booking.domain.Invoice.findAllInvoices();
        org.junit.Assert.assertNotNull("Find all method for 'Invoice' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'Invoice' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void InvoiceIntegrationTest.testFindInvoiceEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'Invoice' failed to initialize correctly", dod.getRandomInvoice());
        long count = org.adorsys.geshotel.booking.domain.Invoice.countInvoices();
        if (count > 20) count = 20;
        java.util.List<org.adorsys.geshotel.booking.domain.Invoice> result = org.adorsys.geshotel.booking.domain.Invoice.findInvoiceEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'Invoice' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'Invoice' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void InvoiceIntegrationTest.testFlush() {
        org.adorsys.geshotel.booking.domain.Invoice obj = dod.getRandomInvoice();
        org.junit.Assert.assertNotNull("Data on demand for 'Invoice' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Invoice' failed to provide an identifier", id);
        obj = org.adorsys.geshotel.booking.domain.Invoice.findInvoice(id);
        org.junit.Assert.assertNotNull("Find method for 'Invoice' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyInvoice(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'Invoice' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void InvoiceIntegrationTest.testMerge() {
        org.adorsys.geshotel.booking.domain.Invoice obj = dod.getRandomInvoice();
        org.junit.Assert.assertNotNull("Data on demand for 'Invoice' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Invoice' failed to provide an identifier", id);
        obj = org.adorsys.geshotel.booking.domain.Invoice.findInvoice(id);
        boolean modified =  dod.modifyInvoice(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        org.adorsys.geshotel.booking.domain.Invoice merged =  obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'Invoice' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void InvoiceIntegrationTest.testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'Invoice' failed to initialize correctly", dod.getRandomInvoice());
        org.adorsys.geshotel.booking.domain.Invoice obj = dod.getNewTransientInvoice(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'Invoice' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'Invoice' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'Invoice' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void InvoiceIntegrationTest.testRemove() {
        org.adorsys.geshotel.booking.domain.Invoice obj = dod.getRandomInvoice();
        org.junit.Assert.assertNotNull("Data on demand for 'Invoice' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Invoice' failed to provide an identifier", id);
        obj = org.adorsys.geshotel.booking.domain.Invoice.findInvoice(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'Invoice' with identifier '" + id + "'", org.adorsys.geshotel.booking.domain.Invoice.findInvoice(id));
    }
    
}
