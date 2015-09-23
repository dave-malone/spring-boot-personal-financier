package io.dmalone.personalfinancier.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import io.dmalone.personalfinancier.model.Budget;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class BudgetRepositoryImpl implements BudgetRepositoryCustom {

	private final MongoTemplate mongoTemplate;
	
	@Autowired
	public BudgetRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Budget getByDate(final Date date) {
		Budget budget = mongoTemplate.findOne(
				query(where("startDate").lte(date).and("endDate").gte(date)), 
				Budget.class);
		
		return budget;
	}

}
