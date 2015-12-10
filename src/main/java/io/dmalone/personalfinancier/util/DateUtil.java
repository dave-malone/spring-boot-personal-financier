package io.dmalone.personalfinancier.util;

import java.util.Calendar;
import java.util.Date;


public class DateUtil {

	public static Calendar getZeroedOutCalendar() {
		return getZeroedOutCalendar(new Date());
	}
	
	public static Calendar getZeroedOutCalendar(Date date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}
	
}
