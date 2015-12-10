package io.dmalone.personalfinancier.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

public class BudgetTest {

	private Budget budget;
	private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	@Before
	public void setUp() throws Exception {
		budget = new Budget();
		budget.setNumberOfDaysWithinPayPeriod(14);
		
		Date startDate = dateFormat.parse("02/06/2015");
		Date endDate = dateFormat.parse("02/19/2015");
		
		budget.setStartDate(startDate);
		budget.setEndDate(endDate);
	}

	@Test
	public void testGetTotalPlannedIncome() {
		budget.addPlannedIncome(new Income("Test 1", "50.25", IncomeFrequency.BiWeekly));
		budget.addPlannedIncome(new Income("Test 2", "50.25", IncomeFrequency.BiWeekly));
		
		BigDecimal totalPlannedIncome = budget.getTotalPlannedIncome();
		assertNotNull("planned income should not be null", totalPlannedIncome);
		assertEquals("total planned income did not add up to the correct amount", new BigDecimal("100.50"), totalPlannedIncome);
	}

	@Test
	public void testGetTotalUnplannedIncome() {
		budget.addUnplannedIncome(new Income("Test 1", "3.72", IncomeFrequency.OneTime));
		budget.addUnplannedIncome(new Income("Test 2", "100.85", IncomeFrequency.OneTime));
		
		BigDecimal totalUnplannedIncome = budget.getTotalUnplannedIncome();
		assertNotNull("unplanned income should not be null", totalUnplannedIncome);
		assertEquals("total unplanned income did not add up to the correct amount", new BigDecimal("104.57"), totalUnplannedIncome);	
	}

	@Test
	public void testGetTotalIncome() {
		budget.addPlannedIncome(new Income("Test 1", "3.72", IncomeFrequency.BiWeekly));
		budget.addUnplannedIncome(new Income("Test 2", "100.85", IncomeFrequency.OneTime));
		
		BigDecimal totalIncome = budget.getTotalIncome();
		assertNotNull("total income should not be null", totalIncome);
		assertEquals("total income did not add up to the correct amount", new BigDecimal("104.57"), totalIncome);
	}

	@Test
	public void testGetTotalPlannedExpenses() {
		budget.addPlannedExpense(new Expense("Test Planned Expense 1", "3.72", ExpenseType.Monthly));
		budget.addPlannedExpense(new Expense("Test Planned Expense 2", "100.85", ExpenseType.PerPaycheck));
		
		BigDecimal totalPlannedExpenses = budget.getTotalPlannedExpenses();
		assertNotNull("total planned expenses should not be null", totalPlannedExpenses);
		assertEquals("total planned expenses did not add up to the correct amount", new BigDecimal("104.57"), totalPlannedExpenses);
	}

	@Test
	public void testGetTotalUnplannedExpenses() {
		budget.addUnplannedExpense(new Expense("Test Unplanned Expense 1", "3.72", ExpenseType.Monthly));
		budget.addUnplannedExpense(new Expense("Test Unplanned Expense 2", "100.85", ExpenseType.PerPaycheck));
		
		BigDecimal totalUnplannedExpenses = budget.getTotalUnplannedExpenses();
		assertNotNull("total unplanned expenses should not be null", totalUnplannedExpenses);
		assertEquals("total unplanned expenses did not add up to the correct amount", new BigDecimal("104.57"), totalUnplannedExpenses);
	}

	@Test
	public void testGetTotalExpenses() {
		budget.addPlannedExpense(new Expense("Test Unplanned Expense 1", "3.72", ExpenseType.Monthly));
		budget.addUnplannedExpense(new Expense("Test Unplanned Expense 2", "100.85", ExpenseType.PerPaycheck));
		
		BigDecimal totalExpenses = budget.getTotalExpenses();
		assertNotNull("total expenses should not be null", totalExpenses);
		assertEquals("total expenses did not add up to the correct amount", new BigDecimal("104.57"), totalExpenses);
	}

	@Test
	public void testGetRemainderAfterExpenses() {
		budget.addPlannedExpense(new Expense("Test Unplanned Expense 1", "3.72", ExpenseType.Monthly));
		budget.addUnplannedExpense(new Expense("Test Unplanned Expense 2", "100.85", ExpenseType.PerPaycheck));
		budget.addPlannedIncome(new Income("Test 1", "3.72", IncomeFrequency.BiWeekly));
		budget.addUnplannedIncome(new Income("Test 2", "100.85", IncomeFrequency.BiWeekly));
		
		
		BigDecimal remainderAfterExpenses = budget.getRemainderAfterExpenses();
		assertNotNull("remainder after expenses should not be null", remainderAfterExpenses);
		assertEquals("remainder after expenses did not add up to the correct amount", new BigDecimal("0.00"), remainderAfterExpenses);
	}

	@Test
	public void testGetDatesInPayPeriod() throws Exception{
		Set<Date> expectedDates = new HashSet<Date>();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(budget.getStartDate());
		
		for(int i = 0; i < budget.getNumberOfDaysWithinPayPeriod(); i++){
			expectedDates.add(calendar.getTime());
			calendar.add(Calendar.DATE, 1);
		}
		
		assertEquals(expectedDates.size(), budget.getDatesInPayPeriod().size());
		assertEquals(expectedDates, budget.getDatesInPayPeriod());
	}
	
	@Test
	public void testGetDaysOfMonthWithinPeriod(){
		Set<Integer> expectedDays = new TreeSet<Integer>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(budget.getStartDate());
		
		for(int i = 0; i < budget.getNumberOfDaysWithinPayPeriod(); i++){
			expectedDays.add(calendar.get(Calendar.DATE));
			calendar.add(Calendar.DATE, 1);
		}
		
		assertEquals(expectedDays.size(), budget.getDaysOfMonthWithinPeriod().size());
		assertEquals(budget.getDaysOfMonthWithinPeriod(), expectedDays);
	}
	
	@Test 
	public void testFromPrimaryIncome() throws Exception{
		Date incomeStartDate = Income.DATE_FORMAT.parse("Feb-06-2015");
		
		Income income = new Income();
		income.setActive(true);
		income.setPrimary(true);
		income.setIncomeFrequency(IncomeFrequency.BiWeekly);
		income.setStartDate(incomeStartDate);
		
		Date today = Income.DATE_FORMAT.parse("Feb-08-2015");
		Date expectedStartDate = Income.DATE_FORMAT.parse("Feb-06-2015");
		Date expectedEndDate = Income.DATE_FORMAT.parse("Feb-20-2015");
		
		Budget budget = Budget.fromPrimaryIncome(today, income);
		assertNotNull(budget);
		assertEquals(BudgetType.BiWeekly, budget.getBudgetType());
		assertEquals(new Integer(14), budget.getNumberOfDaysWithinPayPeriod());
		assertEquals(expectedStartDate, budget.getStartDate());
		assertEquals(expectedEndDate, budget.getEndDate());
	}
	
}
