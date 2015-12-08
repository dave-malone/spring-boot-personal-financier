package io.dmalone.personalfinancier.conversion;

import io.dmalone.personalfinancier.model.Expense;
import io.dmalone.personalfinancier.service.ExpenseService;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;

public class ExpenseItemWriter implements ItemWriter<Expense> {

	private final Log log = LogFactory.getLog(ExpenseItemWriter.class);

	private final ExpenseService expenseService;
	
	public ExpenseItemWriter(ExpenseService expenseService) {
		this.expenseService = expenseService;
	}

	@Override
	public void write(List<? extends Expense> items) throws Exception {
		log.debug("writing expenses: " + items);
		expenseService.save(items);
	}

}
