package io.dmalone.personalfinancier.conversion;

import io.dmalone.personalfinancier.model.Expense;
import io.dmalone.personalfinancier.model.ExpenseType;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;

public class ExpenseItemProcessor implements ItemProcessor<ExpenseData, Expense> {

	private final Log log = LogFactory.getLog(ExpenseItemProcessor.class);
	
	@Override
	public Expense process(ExpenseData item) throws Exception {
		log.debug("processsing expense data: " + item);
		
		Expense expense = new Expense();
		expense.setActive(item.isActive().equals("\0") != true);
		expense.setAmount(item.getAmount());
		expense.setDateCreated(new Date());
		expense.setDayOfMonthDue(item.getDueDate());
		expense.setEndDate(item.getEnd());
		
		ExpenseType expenseType = null;
		if("MONTHLY".equals(item.getExpenseType())){
			expenseType = ExpenseType.Monthly;
		}else if("ONE_TIME".equals(item.getExpenseType())){
			expenseType = ExpenseType.OneTime;
		}else if("PER_PAYCHECK".equals(item.getExpenseType())){
			expenseType = ExpenseType.PerPaycheck;
		}
		expense.setExpenseType(expenseType);
		
		if(ExpenseType.OneTime == expenseType){
			expense.setDueDate(item.getStart());
		}
		
		expense.setName(item.getName());
		expense.setStartDate(item.getStart());
		
		log.debug("converted expense: " + expense);
		
		return expense;
	}

}
