package io.dmalone.personalfinancier.repository;

import io.dmalone.personalfinancier.model.Expense;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExpenseRepository extends MongoRepository<Expense, String>{
	
}