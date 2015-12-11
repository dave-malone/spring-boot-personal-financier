package io.dmalone.personalfinancier.repository;

import io.dmalone.personalfinancier.model.Budget;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BudgetRepository extends MongoRepository<Budget, String>, BudgetRepositoryCustom{
	
}