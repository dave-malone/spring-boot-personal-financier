package io.dmalone.personalfinancier.repository;

import static org.junit.Assert.assertNotNull;
import io.dmalone.personalfinancier.PersonalfinancierApplication;
import io.dmalone.personalfinancier.model.Budget;
import io.dmalone.personalfinancier.model.BudgetType;

import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PersonalfinancierApplication.class)
public class BudgetRepositoryIntegrationTests {

	@Autowired
	private BudgetRepository budgetRepository;
	
	@Before
	public void setUp() throws Exception {
		assertNotNull(budgetRepository);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		System.out.println("current date: " + calendar.getTime());
		
		while(calendar.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY){
			calendar.add(Calendar.DATE, -1);
		}
		
		Date startDate = calendar.getTime();
		
		calendar.add(Calendar.DATE, 13);
		Date endDate = calendar.getTime();
		
		Budget budget = new Budget();
		budget.setBudgetType(BudgetType.BiWeekly);
		budget.setStartDate(startDate);
		budget.setEndDate(endDate);
		
		budget = budgetRepository.save(budget);
		System.out.println("Budget ID: " + budget.getId());
		assertNotNull(budget.getId());
	}
	
	@After
	public void tearDown(){
//		budgetRepository.deleteAll();
	}

	@Test
	@Rollback(true)
	public void testGetByDate() {
		Budget budget = budgetRepository.getByDate(new Date());
		assertNotNull(budget);
	}

}
