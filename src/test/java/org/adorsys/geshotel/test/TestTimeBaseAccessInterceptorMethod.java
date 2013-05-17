package org.adorsys.geshotel.test;

import java.util.List;

import junit.framework.Assert;

import org.adorsys.geshotel.domain.DataInitializerHelper;
import org.adorsys.geshotel.resto.PeriodConfDataOnDemand;
import org.adorsys.geshotel.resto.domain.PeriodConf;
import org.adorsys.geshotel.web.ext.TimeBaseAccessInterceptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(value=SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:META-INF/spring/applicationContext*.xml"})
@Transactional
public class TestTimeBaseAccessInterceptorMethod {
	
	@Autowired
	private PeriodConfDataOnDemand confDataOnDemand;
	
	@Test
	public void testPeriodConfInited(){
		List<PeriodConf> findAllPeriodConfs = PeriodConf.findAllPeriodConfs();
		Assert.assertTrue(findAllPeriodConfs.isEmpty());//if empty mean that data aren't initialized
	}
	
//	@Test
	public void testGetTime(){
		int expected = 15; //give an hour
		TimeBaseAccessInterceptor timeBaseAccessInterceptor = new TimeBaseAccessInterceptor();
		int actual = timeBaseAccessInterceptor.getCurrentHour();
		Assert.assertEquals(expected, actual);
	}
//	@Tests
	public void testCheckPeriod(){
		PeriodConf periodConf = new PeriodConf();
		periodConf.setTitle(DataInitializerHelper.DAY_SERVICE_NAME);
		periodConf.setOpeningTime(7);
		periodConf.setClosingTime(15);
		periodConf.persist();
		TimeBaseAccessInterceptor timeBaseAccessInterceptor = new TimeBaseAccessInterceptor();
		Assert.assertTrue(timeBaseAccessInterceptor.checkPeriod(periodConf));
	}
}
