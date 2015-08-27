package io.dmalone.personalfinancier.repository;

import io.dmalone.personalfinancier.model.Expense;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "expense", path = "expense")
public interface ExpenseRepository extends MongoRepository<Expense, String>{
	
}