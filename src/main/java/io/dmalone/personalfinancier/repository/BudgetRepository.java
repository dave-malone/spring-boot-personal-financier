package io.dmalone.personalfinancier.repository;

import io.dmalone.personalfinancier.model.Budget;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends MongoRepository<Budget, String>{
	
}