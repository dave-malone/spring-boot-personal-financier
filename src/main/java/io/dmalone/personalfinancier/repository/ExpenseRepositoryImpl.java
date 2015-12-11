package io.dmalone.personalfinancier.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import io.dmalone.personalfinancier.model.Budget;
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


	@Override
	public List<Expense> getActiveExpenses() {
		List<Expense> activeExpenses = mongoTemplate.find(query(where("active").is(true)), Expense.class);
		return activeExpenses;
	}
	
	@Override
	public List<Expense> getActiveOneTimeExpenses(Budget budget){
		//Date dueDate
		List<Expense> oneTimeExpenses = mongoTemplate.find(query(
				where("expenseType").is(ExpenseType.OneTime)
				.and("active").is(true)
				.and("dueDate").gte(budget.getStartDate()).lte(budget.getEndDate())), 
			Expense.class);
		return oneTimeExpenses;
	}
	
	@Override
	public List<Expense> getActiveMonthlyExpenses(Budget budget){
		//TODO - project query based on dates within budget period
		//Integer dayOfMonthDue & Date startDate & Date endDate
		List<Expense> monthlyExpenses = mongoTemplate.find(query(
				where("expenseType").is(ExpenseType.Monthly)
				.and("active").is(true)), 
			Expense.class);
		return monthlyExpenses;
	}
	
	@Override
	public List<Expense> getActivePerPaycheckExpenses(Budget budget){
		//TODO - project query based on dates within budget period
		//Date startDate & Date endDate
		List<Expense> perPaycheckExpenses = mongoTemplate.find(query(
				where("expenseType").is(ExpenseType.PerPaycheck)
				.and("active").is(true)), 
			Expense.class);
		return perPaycheckExpenses;
	}


	@Override
	public Map<ExpenseType, List<Expense>> getAllActiveExpensesByType() {
		final Map<ExpenseType, List<Expense>> expensesByType = new TreeMap<ExpenseType, List<Expense>>();
		
		for(ExpenseType expenseType : ExpenseType.values()){
			List<Expense> expenses = mongoTemplate.find(
					query(where("expenseType").is(expenseType).and("active").is(true)), 
					Expense.class);
			expensesByType.put(expenseType, expenses);
		}
		
		return expensesByType;
	}


	@Override
	public Map<ExpenseType, List<Expense>> getAllInactiveExpensesByType() {
		final Map<ExpenseType, List<Expense>> expensesByType = new TreeMap<ExpenseType, List<Expense>>();
		
		for(ExpenseType expenseType : ExpenseType.values()){
			List<Expense> expenses = mongoTemplate.find(
					query(where("expenseType").is(expenseType).and("active").is(false)), 
					Expense.class);
			expensesByType.put(expenseType, expenses);
		}
		
		return expensesByType;
	}
	
	
}
