package io.dmalone.personalfinancier.model;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;

public class IncomeTest {

	@Test
	public void testBiWeeklyGetPayDatesInMonth() throws Exception{
		Date feb062015 = Income.DATE_FORMAT.parse("Feb-06-2015");
		
		Date july102015 = Income.DATE_FORMAT.parse("Jul-10-2015");
		Date july242015 = Income.DATE_FORMAT.parse("Jul-24-2015");
		
		Income income = new Income();
		income.setIncomeFrequency(IncomeFrequency.BiWeekly);
		income.setStartDate(feb062015);
		
		List<Date> payDatesInJuly2015 = income.getPayDatesInMonth(2015, "Jul");
		assertNotNull(payDatesInJuly2015);
		assertFalse(payDatesInJuly2015.isEmpty());
		assertEquals(2, payDatesInJuly2015.size());
		assertTrue(payDatesInJuly2015.contains(july102015));
		assertTrue(payDatesInJuly2015.contains(july242015));
	}
	
	@Test
	public void testOneTimeGetPayDatesInMonth() throws Exception{
		Date july122015 = Income.DATE_FORMAT.parse("Jul-12-2015");
		
		Income income = new Income();
		income.setIncomeFrequency(IncomeFrequency.OneTime);
		income.setDate(july122015);

		List<Date> payDatesInJuly2015 = income.getPayDatesInMonth(2015, "Jul");
		assertNotNull(payDatesInJuly2015);
		assertFalse(payDatesInJuly2015.isEmpty());
		assertEquals(1, payDatesInJuly2015.size());
		assertTrue(payDatesInJuly2015.contains(july122015));
	}
	
	@Test
	public void testBiWeeklyGetPayDatesInMonthWhereDatesLandOnFirstOfTheMonth() throws Exception{
		//TODO
//		Date feb062015 = Income.DATE_FORMAT.parse("Feb-06-2015");
//		
//		Date july102015 = Income.DATE_FORMAT.parse("Jul-10-2015");
//		Date july242015 = Income.DATE_FORMAT.parse("Jul-24-2015");
//		
//		Income income = new Income();
//		income.setIncomeFrequency(IncomeFrequency.BI_WEEKLY);
//		income.setStartDate(feb062015);
//		
//		List<Date> payDatesInJuly2015 = income.getPayDatesInMonth(2015, "Jul");
//		assertNotNull(payDatesInJuly2015);
//		assertFalse(payDatesInJuly2015.isEmpty());
//		assertEquals(2, payDatesInJuly2015.size());
//		assertTrue(payDatesInJuly2015.contains(july102015));
//		assertTrue(payDatesInJuly2015.contains(july242015));
	}

}
