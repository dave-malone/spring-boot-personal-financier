package io.dmalone.personalfinancier.repository;

import io.dmalone.personalfinancier.model.Expense;
import io.dmalone.personalfinancier.model.ExpenseType;

import java.util.List;
import java.util.Map;

public interface ExpenseRepositoryCustom {

	public Map<ExpenseType, List<Expense>> getAllExpensesByType();
	
}
