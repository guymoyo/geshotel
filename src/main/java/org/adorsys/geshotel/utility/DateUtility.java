package org.adorsys.geshotel.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtility {
	public static Date makeDate(String dateString, String pattern)
			throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.parse(dateString);
	}

	public static int countDiffDay(Calendar c1, Calendar c2) {
		int returnInt = 0;
		while (!c1.after(c2)) {
			c1.add(Calendar.DAY_OF_MONTH, 1);
			returnInt++;
		}

		if (returnInt > 0) {
			returnInt = returnInt - 1;
		}

		return (returnInt);
	}

	public static int diff(Date date1, Date date2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(date1);
		c2.setTime(date2);
		int diffDay = 0;

		if (c1.before(c2)) {
			diffDay = countDiffDay(c1, c2);
		} else {
			diffDay = countDiffDay(c2, c1);
		}

		return diffDay;
	}

	public static Date addDayToDate(Date date, int day) {
		GregorianCalendar grecal = new GregorianCalendar();
		grecal.setTime(date);
		grecal.add(GregorianCalendar.DAY_OF_MONTH, day);
		return grecal.getTime();
	}

	public static Date addHourToTime(Date date, int hour) {
		GregorianCalendar grecal = new GregorianCalendar();
		grecal.setTime(date);
		grecal.add(GregorianCalendar.HOUR_OF_DAY, hour);
		return grecal.getTime();
	}

	// return the nber of hour since 1 janvier 1970
	public static long diffInHour(Date date1, Date date2) {
		long time1 = date1.getTime();
		long time2 = date2.getTime();
		long result;
		if (date1.after(date2))
			result = time1 - time2;
		else
			result = time2 - time1;

	
		return result / 3600000;
	}
	
	public static Date buildDate(Date date){
		
		Calendar cal = Calendar.getInstance();       // get calendar instance
		cal.setTime(date);                           // set cal to date
		cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
		cal.set(Calendar.MINUTE, 0);                 // set minute in hour
		cal.set(Calendar.SECOND, 0);                 // set second in minute
		cal.set(Calendar.MILLISECOND, 0); 
		return cal.getTime();
	}

}
