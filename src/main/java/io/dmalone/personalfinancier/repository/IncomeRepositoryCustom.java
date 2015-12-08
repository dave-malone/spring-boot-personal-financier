package io.dmalone.personalfinancier.repository;

import io.dmalone.personalfinancier.model.Income;
import io.dmalone.personalfinancier.model.IncomeFrequency;

import java.util.List;
import java.util.Map;

public interface IncomeRepositoryCustom {

	public Map<IncomeFrequency, List<Income>> getAllIncomesByFrequency();
	public List<Income> getActiveIncome();
	Income getPrimaryIncome();
	
}
