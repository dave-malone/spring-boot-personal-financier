package io.dmalone.personalfinancier.conversion;

import io.dmalone.personalfinancier.model.Income;
import io.dmalone.personalfinancier.service.IncomeService;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;

public class IncomeItemWriter implements ItemWriter<Income> {

	private final Log log = LogFactory.getLog(IncomeItemWriter.class);

	private final IncomeService incomeService;
	
	public IncomeItemWriter(IncomeService incomeService) {
		this.incomeService = incomeService;
	}

	@Override
	public void write(List<? extends Income> items) throws Exception {
		log.debug("writing income: " + items);
		for(Income income : items){
			income.setPrimary(true);
			income.setActive(true);
			incomeService.save(income);
		}
	}

}
