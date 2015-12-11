package io.dmalone.personalfinancier.util;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

public class DateRangeTest {

	private static final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	private DateRange dateRange;
	private Date startDate;
	private Date endDate;
	private int expectedNumberOfDaysInRange = 14;
	
	@Before
	public void setUp() throws Exception {
		startDate = dateFormat.parse("02/06/2015");
		endDate = dateFormat.parse("02/19/2015");
		
		dateRange = new DateRange(startDate, endDate);
	}

	@Test
	public void testIsDateInRange() throws Exception {
		assertTrue(dateRange.isDateInRange(dateFormat.parse("02/11/2015")));
		assertTrue(dateRange.isDateInRange(startDate));
		assertTrue(dateRange.isDateInRange(endDate));
		assertFalse(dateRange.isDateInRange(dateFormat.parse("02/05/2015")));
		assertFalse(dateRange.isDateInRange(dateFormat.parse("02/20/2015")));
	}


	@Test
	public void testIsDayOfMonthWithinRange() {
		assertTrue(dateRange.isDayOfMonthWithinRange(8));
		assertTrue(dateRange.isDayOfMonthWithinRange(6));
		assertTrue(dateRange.isDayOfMonthWithinRange(19));
		assertFalse(dateRange.isDayOfMonthWithinRange(5));
		assertFalse(dateRange.isDayOfMonthWithinRange(20));
	}
	
	@Test
	public void testGetNumberOfDaysInPeriod(){
		assertEquals(expectedNumberOfDaysInRange, dateRange.getNumberOfDaysInPeriod());
	}

	@Test
	public void testGetDates() throws Exception{
		Set<Date> expectedDates = new HashSet<Date>();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		
		for(int i = 0; i < expectedNumberOfDaysInRange; i++){
			expectedDates.add(calendar.getTime());
			calendar.add(Calendar.DATE, 1);
		}
		
		assertEquals(expectedDates.size(), dateRange.getDates().size());
		assertEquals(expectedDates, dateRange.getDates());
	}
	
	@Test
	public void testGetDays(){
		Set<Integer> expectedDays = new TreeSet<Integer>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		
		for(int i = 0; i < expectedNumberOfDaysInRange; i++){
			expectedDays.add(calendar.get(Calendar.DATE));
			calendar.add(Calendar.DATE, 1);
		}
		
		assertEquals(expectedDays.size(), dateRange.getDays().size());
		assertEquals(expectedDays, dateRange.getDays());
	}

}
