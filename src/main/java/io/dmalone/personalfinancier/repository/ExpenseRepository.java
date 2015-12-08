package io.dmalone.personalfinancier.repository;

import io.dmalone.personalfinancier.model.Expense;
import io.dmalone.personalfinancier.model.ExpenseType;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExpenseRepository extends MongoRepository<Expense, String>, ExpenseRepositoryCustom{
	
	public List<Expense> findByExpenseType(ExpenseType expenseType);

}