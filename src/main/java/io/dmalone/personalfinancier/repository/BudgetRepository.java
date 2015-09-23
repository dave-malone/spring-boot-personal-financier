package io.dmalone.personalfinancier.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.dmalone.personalfinancier.model.Budget;

public interface BudgetRepository extends MongoRepository<Budget, String>, BudgetRepositoryCustom{
	
}