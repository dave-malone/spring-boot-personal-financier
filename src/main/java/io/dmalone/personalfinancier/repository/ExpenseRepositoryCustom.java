package io.dmalone.personalfinancier.repository;

import io.dmalone.personalfinancier.model.Budget;
import io.dmalone.personalfinancier.model.Expense;
import io.dmalone.personalfinancier.model.ExpenseType;

import java.util.List;
import java.util.Map;

public interface ExpenseRepositoryCustom {

	Map<ExpenseType, List<Expense>> getAllExpensesByType();

	List<Expense> getActiveExpenses();

	List<Expense> getActivePerPaycheckExpenses(Budget budget);

	List<Expense> getActiveMonthlyExpenses(Budget budget);

	List<Expense> getActiveOneTimeExpenses(Budget budget);
	
}
