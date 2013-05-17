// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.adorsys.geshotel.domain;

import org.adorsys.geshotel.domain.PaymentDataOnDemand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect PaymentIntegrationTest_Roo_IntegrationTest {
    
    declare @type: PaymentIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: PaymentIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml");
    
    declare @type: PaymentIntegrationTest: @Transactional;
    
    @Autowired
    private PaymentDataOnDemand PaymentIntegrationTest.dod;
    
    @Test
    public void PaymentIntegrationTest.testCountPayments() {
        org.junit.Assert.assertNotNull("Data on demand for 'Payment' failed to initialize correctly", dod.getRandomPayment());
        long count = org.adorsys.geshotel.domain.Payment.countPayments();
        org.junit.Assert.assertTrue("Counter for 'Payment' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void PaymentIntegrationTest.testFindPayment() {
        org.adorsys.geshotel.domain.Payment obj = dod.getRandomPayment();
        org.junit.Assert.assertNotNull("Data on demand for 'Payment' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Payment' failed to provide an identifier", id);
        obj = org.adorsys.geshotel.domain.Payment.findPayment(id);
        org.junit.Assert.assertNotNull("Find method for 'Payment' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'Payment' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void PaymentIntegrationTest.testFindAllPayments() {
        org.junit.Assert.assertNotNull("Data on demand for 'Payment' failed to initialize correctly", dod.getRandomPayment());
        long count = org.adorsys.geshotel.domain.Payment.countPayments();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'Payment', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<org.adorsys.geshotel.domain.Payment> result = org.adorsys.geshotel.domain.Payment.findAllPayments();
        org.junit.Assert.assertNotNull("Find all method for 'Payment' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'Payment' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void PaymentIntegrationTest.testFindPaymentEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'Payment' failed to initialize correctly", dod.getRandomPayment());
        long count = org.adorsys.geshotel.domain.Payment.countPayments();
        if (count > 20) count = 20;
        java.util.List<org.adorsys.geshotel.domain.Payment> result = org.adorsys.geshotel.domain.Payment.findPaymentEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'Payment' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'Payment' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void PaymentIntegrationTest.testFlush() {
        org.adorsys.geshotel.domain.Payment obj = dod.getRandomPayment();
        org.junit.Assert.assertNotNull("Data on demand for 'Payment' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Payment' failed to provide an identifier", id);
        obj = org.adorsys.geshotel.domain.Payment.findPayment(id);
        org.junit.Assert.assertNotNull("Find method for 'Payment' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyPayment(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'Payment' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void PaymentIntegrationTest.testMerge() {
        org.adorsys.geshotel.domain.Payment obj = dod.getRandomPayment();
        org.junit.Assert.assertNotNull("Data on demand for 'Payment' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Payment' failed to provide an identifier", id);
        obj = org.adorsys.geshotel.domain.Payment.findPayment(id);
        boolean modified =  dod.modifyPayment(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        org.adorsys.geshotel.domain.Payment merged =  obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'Payment' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void PaymentIntegrationTest.testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'Payment' failed to initialize correctly", dod.getRandomPayment());
        org.adorsys.geshotel.domain.Payment obj = dod.getNewTransientPayment(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'Payment' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'Payment' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'Payment' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void PaymentIntegrationTest.testRemove() {
        org.adorsys.geshotel.domain.Payment obj = dod.getRandomPayment();
        org.junit.Assert.assertNotNull("Data on demand for 'Payment' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Payment' failed to provide an identifier", id);
        obj = org.adorsys.geshotel.domain.Payment.findPayment(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'Payment' with identifier '" + id + "'", org.adorsys.geshotel.domain.Payment.findPayment(id));
    }
    
}
