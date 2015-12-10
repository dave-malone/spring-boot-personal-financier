package io.dmalone.personalfinancier.service;

import io.dmalone.personalfinancier.model.Budget;
import io.dmalone.personalfinancier.model.BudgetType;
import io.dmalone.personalfinancier.model.Expense;
import io.dmalone.personalfinancier.model.ExpenseType;
import io.dmalone.personalfinancier.model.Income;
import io.dmalone.personalfinancier.model.IncomeFrequency;
import io.dmalone.personalfinancier.repository.BudgetRepository;
import io.dmalone.personalfinancier.repository.IncomeRepository;
import io.dmalone.personalfinancier.util.DateUtil;

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
		final Date today = DateUtil.getZeroedOutCalendar().getTime();
		Budget currentBudget = this.budgetRepository.getByDate(today);
		
		if(currentBudget == null){
			Income primaryIncome = incomeRepository.getPrimaryIncome();
			currentBudget = generateBudget(today, primaryIncome);
		}
		
		return currentBudget;
	}
	
	
	private Budget generateBudget(Date forDate, Income primaryIncome){
		final Budget budget = Budget.fromPrimaryIncome(forDate, primaryIncome);
		
		
		List<Expense> expenses = expenseService.getPlannedExpensesForBudget(budget);
		budget.addPlannedExpenses(expenses);
		
		return budget;
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
