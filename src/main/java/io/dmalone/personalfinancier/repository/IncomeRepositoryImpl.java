package io.dmalone.personalfinancier.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import io.dmalone.personalfinancier.model.Income;
import io.dmalone.personalfinancier.model.IncomeFrequency;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class IncomeRepositoryImpl implements IncomeRepositoryCustom{

	private final MongoTemplate mongoTemplate;
	
	@Autowired
	public IncomeRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Map<IncomeFrequency, List<Income>> getAllIncomesByFrequency() {
		final Map<IncomeFrequency, List<Income>> incomeByFrequency = new TreeMap<IncomeFrequency, List<Income>>();
		
		for(IncomeFrequency incomeFrequency : IncomeFrequency.values()){
			List<Income> expenses = mongoTemplate.find(
					query(where("incomeFrequency").is(incomeFrequency)), 
					Income.class);
			incomeByFrequency.put(incomeFrequency, expenses);
		}
		
		return incomeByFrequency;
	}
	
}
