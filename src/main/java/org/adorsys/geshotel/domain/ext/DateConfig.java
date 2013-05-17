package org.adorsys.geshotel.domain.ext;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

public abstract class DateConfig {

	public static DateConfigPeriod getBegingEndOfDay(Date date){
		Date beginDate = DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
		Date enDate = DateUtils.addDays(beginDate, 1);
		enDate = DateUtils.addSeconds(enDate, -1);
		return new DateConfigPeriod(beginDate, enDate);
	}
	
	public static DateConfigPeriod getBegingEndOfWeek(Date date){
		Date beginDate = DateUtils.truncate(date, Calendar.WEEK_OF_MONTH);
		Date enDate = DateUtils.addWeeks(beginDate, 1);
		enDate = DateUtils.addSeconds(enDate, -1);
		return new DateConfigPeriod(beginDate, enDate);
	}
	
	public static DateConfigPeriod getBegingEndOfMonth(Date date){
		Date beginDate = DateUtils.truncate(date, Calendar.WEEK_OF_MONTH);
		Date enDate = DateUtils.addMonths(beginDate, 1);
		enDate = DateUtils.addSeconds(enDate, -1);
		return new DateConfigPeriod(beginDate, enDate);
	}
	
}
