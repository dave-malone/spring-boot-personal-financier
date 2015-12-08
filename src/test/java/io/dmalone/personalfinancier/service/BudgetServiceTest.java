package io.dmalone.personalfinancier.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import io.dmalone.personalfinancier.model.Budget;
import io.dmalone.personalfinancier.repository.BudgetRepository;
import io.dmalone.personalfinancier.repository.ExpenseRepository;
import io.dmalone.personalfinancier.repository.IncomeRepository;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class BudgetServiceTest {

	private BudgetRepository mockBudgetRepository;
	private ExpenseRepository mockExpenseRepository;
	private IncomeRepository mockIncomeRepository;
	private BudgetService budgetService;

	@Before
	public void setUp() throws Exception {
		mockBudgetRepository = mock(BudgetRepository.class);
		mockExpenseRepository = mock(ExpenseRepository.class);
		mockIncomeRepository = mock(IncomeRepository.class);
		budgetService = new BudgetService(mockBudgetRepository, mockExpenseRepository, mockIncomeRepository);
	}

	@Test
	public void testGetCurrentBudget() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		Date testDate = calendar.getTime();
		
		Budget expected = new Budget();
		when(mockBudgetRepository.getByDate(testDate)).thenReturn(expected);
		
		Budget currentBudget = budgetService.getCurrentBudget();
		assertNotNull(currentBudget);
		assertEquals(expected, currentBudget);
	}

	@Test
	public void testAddExpense() {
		//fail("Not yet implemented");
	}

	@Test
	public void testAddIncome() {
		//fail("Not yet implemented");
	}

	@Test
	public void testRemoveExpense() {
		//fail("Not yet implemented");
	}

	@Test
	public void testRemoveIncome() {
		//fail("Not yet implemented");
	}

}
