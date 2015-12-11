package io.dmalone.personalfinancier.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import io.dmalone.personalfinancier.util.DateRange;

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
	public void testBiWeeklyGetPayDatesInMonthWhereDatesLandOnFirstOfTheMonthContainsAllExpectedDates() throws Exception{
		Date incomeStartDate = Income.DATE_FORMAT.parse("Feb-06-2015");
		
		Date firstExpectedPayDate = Income.DATE_FORMAT.parse("May-01-2015");
		Date secondExpectedPayDate = Income.DATE_FORMAT.parse("May-15-2015");
		Date thirdExpectedPayDate = Income.DATE_FORMAT.parse("May-29-2015");
		
		Income income = new Income();
		income.setIncomeFrequency(IncomeFrequency.BiWeekly);
		income.setStartDate(incomeStartDate);
		
		List<Date> payDatesInMay2015 = income.getPayDatesInMonth(2015, "May");
		assertNotNull(payDatesInMay2015);
		assertFalse(payDatesInMay2015.isEmpty());
		assertEquals(3, payDatesInMay2015.size());
		assertTrue(payDatesInMay2015.contains(firstExpectedPayDate));
		assertTrue(payDatesInMay2015.contains(secondExpectedPayDate));
		assertTrue(payDatesInMay2015.contains(thirdExpectedPayDate));
	}
	
	@Test
	public void testGetPayPeriodStartAndEndDates() throws Exception{
		Date incomeStartDate = Income.DATE_FORMAT.parse("Feb-06-2015");
		Date today = Income.DATE_FORMAT.parse("Dec-10-2015");
		Date expectedStartDate = Income.DATE_FORMAT.parse("Nov-27-2015");
		Date expectedEndDate = Income.DATE_FORMAT.parse("Dec-10-2015");
		
		Income income = new Income();
		income.setIncomeFrequency(IncomeFrequency.BiWeekly);
		income.setStartDate(incomeStartDate);
		income.setActive(true);
		income.setPrimary(true);
		
		DateRange startAndEndDates = income.getPayPeriodDateRange(today);
		assertNotNull(startAndEndDates);
		assertEquals(expectedStartDate, startAndEndDates.getStart());
		assertEquals(expectedEndDate, startAndEndDates.getEnd());
	}

}
