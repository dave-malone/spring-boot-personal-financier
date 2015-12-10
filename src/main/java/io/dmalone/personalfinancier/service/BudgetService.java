package io.dmalone.personalfinancier.service;

import io.dmalone.personalfinancier.model.Budget;
import io.dmalone.personalfinancier.model.BudgetType;
import io.dmalone.personalfinancier.model.Expense;
import io.dmalone.personalfinancier.model.ExpenseType;
import io.dmalone.personalfinancier.model.Income;
import io.dmalone.personalfinancier.model.IncomeFrequency;
import io.dmalone.personalfinancier.repository.BudgetRepository;
import io.dmalone.personalfinancier.repository.IncomeRepository;

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
	
	public Budget getCurrentBudget(){
		final Date today = getZeroedOutCalendar().getTime();
		Budget currentBudget = this.budgetRepository.getByDate(today);
		
		if(currentBudget == null){
			Income primaryIncome = incomeRepository.getPrimaryIncome();
			currentBudget = generateBudget(today, primaryIncome);
		}
		
		return currentBudget;
	}
	
	
	private Budget generateBudget(Date forDate, Income primaryIncome){
		final Budget budget = new Budget();
		budget.addPlannedIncome(primaryIncome);
		
		final Calendar calendar = getZeroedOutCalendar();
		
		int numberOfDaysInPayPeriod = 0;
		
		if(IncomeFrequency.BiWeekly == primaryIncome.getIncomeFrequency()){
			budget.setBudgetType(BudgetType.BiWeekly);
			numberOfDaysInPayPeriod = 13;
		}
		
		budget.setNumberOfDaysWithinPayPeriod(numberOfDaysInPayPeriod);
		
		final Date incomeStartDate = primaryIncome.getStartDate();
		
		calendar.setTime(incomeStartDate);
		Date budgetStartDate = calendar.getTime();
		
		calendar.add(Calendar.DATE, numberOfDaysInPayPeriod);
		Date budgetEndDate = calendar.getTime();
		
		while((forDate.compareTo(budgetStartDate) >= 0 && forDate.compareTo(budgetEndDate) <= 0) != true){
			calendar.add(Calendar.DATE, 1);
			budgetStartDate = calendar.getTime();
			calendar.add(Calendar.DATE, numberOfDaysInPayPeriod);
			budgetEndDate = calendar.getTime();
		}
		
		budget.setStartDate(budgetStartDate);
		budget.setEndDate(budgetEndDate);
		
		List<Expense> expenses = expenseService.getPlannedExpensesForBudget(budget);
		budget.addPlannedExpenses(expenses);
		
		return budget;
	}

	private Calendar getZeroedOutCalendar() {
		final Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}
	
	public void addExpense(Expense expense){
		if(ExpenseType.Monthly == expense.getExpenseType()){
			//TODO - find all current and future budget records and add this expense to it
			expense.getDayOfMonthDue();
			expense.getStartDate();
			expense.getEndDate();
		}else if(ExpenseType.OneTime == expense.getExpenseType()){
			//TODO - find the budget record where the expense's due date is within the
			//budget's start and end dates 
			expense.getDueDate();
		}else if(ExpenseType.PerPaycheck == expense.getExpenseType()){
			//TODO - needs to be mapped against recurring income
			expense.getStartDate();
			expense.getEndDate();
		}
	}
	
	public void addIncome(Income income){
		
	}
	
	public void removeExpense(Expense expense){
		
	}
	
	public void removeIncome(Income income){
		
	}
	
}
