package io.dmalone.personalfinancier.model;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class BudgetTest {

	private Budget budget;
	
	@Before
	public void setUp() throws Exception {
		budget = new Budget();
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

}
