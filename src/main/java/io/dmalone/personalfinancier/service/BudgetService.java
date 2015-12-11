package io.dmalone.personalfinancier.service;

import io.dmalone.personalfinancier.model.Budget;
import io.dmalone.personalfinancier.model.Expense;
import io.dmalone.personalfinancier.model.Income;
import io.dmalone.personalfinancier.repository.BudgetRepository;
import io.dmalone.personalfinancier.repository.IncomeRepository;
import io.dmalone.personalfinancier.util.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BudgetService {

	private final BudgetRepository budgetRepository;
	private final ExpenseService expenseService;
	private final IncomeRepository incomeRepository;

	@Autowired
	public BudgetService(BudgetRepository budgetRepository, ExpenseService expenseService, IncomeRepository incomeRepository) {
		this.budgetRepository = budgetRepository;
		this.incomeRepository = incomeRepository;
		this.expenseService = expenseService;
	}
	
	public List<Budget> generateForecast(int numberOfMonths){
		List<Budget> forecast = new ArrayList<Budget>();
		
		final Calendar calendar = DateUtil.getZeroedOutCalendar();
		for(int i = 0; i < 2 * numberOfMonths; i++){
			Budget budget = getBudgetForDate(calendar.getTime());
			forecast.add(budget);
			calendar.add(Calendar.DATE, 14);
		}
		
		return forecast;
	}
	
	public Budget getBudgetForDate(Date forDate){
		Budget budget = this.budgetRepository.getByDate(forDate);
		
		if(budget == null){
			Income primaryIncome = incomeRepository.getPrimaryIncome();
			budget = generateBudget(forDate, primaryIncome);
		}
		
		return budget;
	}
	
	public Budget getCurrentBudget(){
		final Date today = DateUtil.getZeroedOutCalendar().getTime();
		return getBudgetForDate(today);
	}
	
	
	private Budget generateBudget(Date forDate, Income primaryIncome){
		final Budget budget = Budget.fromPrimaryIncome(forDate, primaryIncome);

		final List<Expense> expenses = expenseService.getPlannedExpensesForBudget(budget);
		budget.addPlannedExpenses(expenses);
		
		return budget;
	}
	
}
