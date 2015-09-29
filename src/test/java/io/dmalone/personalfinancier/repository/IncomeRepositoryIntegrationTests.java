package io.dmalone.personalfinancier.repository;

import static org.junit.Assert.*;
import io.dmalone.personalfinancier.PersonalfinancierApplication;
import io.dmalone.personalfinancier.model.Income;
import io.dmalone.personalfinancier.model.IncomeFrequency;

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
public class IncomeRepositoryIntegrationTests {

	@Autowired
	private IncomeRepository incomeRepository;
	
	@Before
	public void setUp() throws Exception {
		assertNotNull(incomeRepository);
	}
	
	@After
	public void tearDown(){
		incomeRepository.deleteAll();
	}

	@Test
	public void testSave() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		System.out.println("current date: " + calendar.getTime());
		
		while(calendar.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY){
			calendar.add(Calendar.DATE, -1);
		}
		
		Date startDate = calendar.getTime();
		
		Income income = new Income();
		income.setName("Test Bi Weekly Income");
		income.setAmount("500");
		income.setIncomeFrequency(IncomeFrequency.BiWeekly);
		income.setStartDate(startDate);
		
		income = incomeRepository.save(income);
		System.out.println("Expense ID: " + income.getId());
		assertNotNull(income.getId());
	}
	
	@Test
	public void testGetAllIncomesByFrequency(){
		Map<IncomeFrequency, List<Income>> incomeByFrequency = incomeRepository.getAllIncomesByFrequency();
		assertNotNull(incomeByFrequency);
		assertFalse(incomeByFrequency.isEmpty());
	}

}
