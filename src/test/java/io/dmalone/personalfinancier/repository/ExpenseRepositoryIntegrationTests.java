package io.dmalone.personalfinancier.repository;

import static org.junit.Assert.*;
import io.dmalone.personalfinancier.PersonalfinancierApplication;
import io.dmalone.personalfinancier.model.Expense;
import io.dmalone.personalfinancier.model.ExpenseType;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PersonalfinancierApplication.class)
public class ExpenseRepositoryIntegrationTests {

	@Autowired
	private ExpenseRepository expenseRepository;
	
	@Before
	public void setUp() throws Exception {
		assertNotNull(expenseRepository);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		System.out.println("current date: " + calendar.getTime());
		
		while(calendar.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY){
			calendar.add(Calendar.DATE, -1);
		}
		
		Date startDate = calendar.getTime();
		
		calendar.add(Calendar.DATE, 13);
		Date endDate = calendar.getTime();
		
		Expense expense = new Expense();
		expense.setExpenseType(ExpenseType.Monthly);
		expense.setStartDate(startDate);
		expense.setEndDate(endDate);
		
		expense = expenseRepository.save(expense);
		assertNotNull(expense.getId());
	}
	
	@After
	public void tearDown(){
		expenseRepository.deleteAll();
	}

	@Test
	public void testGetAllExpensesByType(){
		Map<ExpenseType, List<Expense>> allExpensesByType = expenseRepository.getAllExpensesByType();
		assertNotNull(allExpensesByType);
		//since there are currently three expense types, there will always be three 
		assertEquals(3, allExpensesByType.size());

		
		assertEquals(0, allExpensesByType.get(ExpenseType.OneTime).size());
		assertEquals(0, allExpensesByType.get(ExpenseType.PerPaycheck).size());
		assertEquals(1, allExpensesByType.get(ExpenseType.Monthly).size());
	}
	

}
