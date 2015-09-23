package io.dmalone.personalfinancier.service;

import io.dmalone.personalfinancier.model.Budget;
import io.dmalone.personalfinancier.model.Expense;
import io.dmalone.personalfinancier.model.ExpenseType;
import io.dmalone.personalfinancier.model.Income;
import io.dmalone.personalfinancier.model.IncomeFrequency;
import io.dmalone.personalfinancier.repository.BudgetRepository;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BudgetService {

	private final BudgetRepository budgetRepository;

	@Autowired
	public BudgetService(BudgetRepository budgetRepository) {
		this.budgetRepository = budgetRepository;
	}
	
	public Budget getCurrentBudget(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return this.budgetRepository.getByDate(calendar.getTime());
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
		//income really drives the creation of a time boxed budget.
		
		if(IncomeFrequency.OneTime == income.getIncomeFrequency()){
			
		}else if(IncomeFrequency.BiWeekly == income.getIncomeFrequency()){
			
		}else if(IncomeFrequency.SemiMonthly == income.getIncomeFrequency()){
			
		}
	}
	
	public void removeExpense(Expense expense){
		
	}
	
	public void removeIncome(Income income){
		
	}
	
}
