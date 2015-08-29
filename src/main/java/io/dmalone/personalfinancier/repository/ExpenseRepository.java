package io.dmalone.personalfinancier.repository;

import io.dmalone.personalfinancier.model.Expense;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends MongoRepository<Expense, String>{
	
}