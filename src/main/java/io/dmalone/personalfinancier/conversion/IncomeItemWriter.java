package io.dmalone.personalfinancier.conversion;

import io.dmalone.personalfinancier.model.Income;
import io.dmalone.personalfinancier.repository.IncomeRepository;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;

public class IncomeItemWriter implements ItemWriter<Income> {

	private final Log log = LogFactory.getLog(IncomeItemWriter.class);

	private final IncomeRepository incomeRepository;
	
	public IncomeItemWriter(IncomeRepository incomeRepository) {
		this.incomeRepository = incomeRepository;
	}

	@Override
	public void write(List<? extends Income> items) throws Exception {
		log.debug("writing income: " + items);
		incomeRepository.save(items);
	}

}
