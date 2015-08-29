package io.dmalone.personalfinancier.repository;

import io.dmalone.personalfinancier.model.Budget;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "budget", path = "budget")
public interface BudgetRepository extends MongoRepository<Budget, String>{
	
}