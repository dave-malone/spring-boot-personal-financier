package io.dmalone.personalfinancier.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import io.dmalone.personalfinancier.model.Expense;
import io.dmalone.personalfinancier.model.ExpenseType;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class ExpenseRepositoryImpl implements ExpenseRepositoryCustom{

	private final MongoTemplate mongoTemplate;
	
	@Autowired
	public ExpenseRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	
	public Map<ExpenseType, List<Expense>> getAllExpensesByType(){
		final Map<ExpenseType, List<Expense>> expensesByType = new TreeMap<ExpenseType, List<Expense>>();
		
		for(ExpenseType expenseType : ExpenseType.values()){
			List<Expense> expenses = mongoTemplate.find(
					query(where("expenseType").is(expenseType)), 
					Expense.class);
			expensesByType.put(expenseType, expenses);
		}
		
		return expensesByType;
	}
	
}
