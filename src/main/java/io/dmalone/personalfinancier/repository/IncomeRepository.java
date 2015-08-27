package io.dmalone.personalfinancier.repository;

import io.dmalone.personalfinancier.model.Income;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "income", path = "income")
public interface IncomeRepository extends MongoRepository<Income, String>{
	
}