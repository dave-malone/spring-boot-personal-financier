package io.dmalone.personalfinancier.repository;

import io.dmalone.personalfinancier.model.Budget;

import java.util.Date;

public interface BudgetRepositoryCustom {

	/**
	 * Returns a Budget where the start date is gte AND the end date is lte the 
	 * given Date parameter, or null if no such budget exists
	 * @param date
	 * @return
	 */
	Budget getByDate(Date date);
	
}
