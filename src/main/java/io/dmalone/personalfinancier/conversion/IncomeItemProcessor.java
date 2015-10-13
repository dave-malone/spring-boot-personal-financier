package io.dmalone.personalfinancier.conversion;

import io.dmalone.personalfinancier.model.Income;
import io.dmalone.personalfinancier.model.IncomeFrequency;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;

public class IncomeItemProcessor implements ItemProcessor<IncomeData, Income> {

	private final Log log = LogFactory.getLog(IncomeItemProcessor.class);
	
	@Override
	public Income process(IncomeData item) throws Exception {
		log.debug("processsing income data: " + item);
		
		Income income = new Income();
		income.setAmount(item.getAmount());
		income.setDate(item.getDate());
		income.setDateCreated(new Date());
		income.setEndDate(item.getEndDate());
		
		IncomeFrequency incomeFrequency = null;
		if("BI_WEEKLY".equals(item.getIncomeFrequency())){
			incomeFrequency = IncomeFrequency.BiWeekly;
		}else if("ONE_TIME".equals(item.getIncomeFrequency())){
			incomeFrequency = IncomeFrequency.OneTime;
		}
		income.setIncomeFrequency(incomeFrequency);
		
		income.setName(item.getName());
		income.setStartDate(item.getStartDate());
		
		log.debug("converted income: " + income);
		
		return income;
	}

}
