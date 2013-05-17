package org.adorsys.geshotel.test;

import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.Date;

import junit.framework.Assert;

import org.adorsys.geshotel.utility.DateUtility;
import org.junit.Before;
import org.junit.Test;

public class TestDateUtil {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public final void testMakeDate(){
		Date makeDate = null;
		try {
			makeDate = DateUtility.makeDate("22-12-2011 14:55", "dd-MM-yyyy HH:mm");
		} catch (ParseException e) {
			fail(e.getMessage());
		}
		System.out.println("Simple Date (no need to test Null Value)  :"+makeDate.toString());
	}

//	@Test
	public final void testCountDiffDay() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testDiff() throws ParseException {
		int expected = 1;
		Date date1 = DateUtility.makeDate("22-12-2011 14:55", "dd-MM-yyyy HH:mm");
		Date date2 = DateUtility.makeDate("24-12-2011 13:55","dd-MM-yyyy HH:mm");
		int actual = DateUtility.diff(date1, date2);
		Assert.assertEquals(expected,actual);
	}

//	@Test
	public final void testAddDayToDate() {
		fail("Not yet implemented"); // TODO
	}

//	@Test
	public final void testAddHourToTime() {
		fail("Not yet implemented"); // TODO
	}

//	@Test
	public final void testDiffInHour() {
		fail("Not yet implemented"); // TODO
	}

}
