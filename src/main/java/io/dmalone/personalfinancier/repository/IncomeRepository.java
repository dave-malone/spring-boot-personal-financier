package io.dmalone.personalfinancier.repository;

import io.dmalone.personalfinancier.model.Income;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface IncomeRepository extends MongoRepository<Income, String>, IncomeRepositoryCustom{

}