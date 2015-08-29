package io.dmalone.personalfinancier.repository;

import io.dmalone.personalfinancier.model.Income;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeRepository extends MongoRepository<Income, String>{
	
}