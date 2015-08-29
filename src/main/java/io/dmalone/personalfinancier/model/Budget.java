package io.dmalone.personalfinancier.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;

/**
 * Represents an actual point-in-time budget. 
 * 
 * @author dmalone
 *
 */
public class Budget {

	@Id private String id;
	private BudgetType budgetType;
	private Date startDate;
	private Date endDate;
	
	private Set<Expense> plannedExpenses = new HashSet<Expense>();
	private Set<Income> plannedIncome = new HashSet<Income>();
	private Set<Expense> unplannedExpenses = new HashSet<Expense>();
	private Set<Income> unplannedIncome = new HashSet<Income>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public BudgetType getBudgetType() {
		return budgetType;
	}
	public void setBudgetType(BudgetType budgetType) {
		this.budgetType = budgetType;
	}
	public Set<Expense> getPlannedExpenses() {
		return plannedExpenses;
	}
	public void setPlannedExpenses(Set<Expense> plannedExpenses) {
		this.plannedExpenses = plannedExpenses;
	}
	public Set<Income> getPlannedIncome() {
		return plannedIncome;
	}
	public void setPlannedIncome(Set<Income> plannedIncome) {
		this.plannedIncome = plannedIncome;
	}
	public Set<Expense> getUnplannedExpenses() {
		return unplannedExpenses;
	}
	public void setUnplannedExpenses(Set<Expense> unplannedExpenses) {
		this.unplannedExpenses = unplannedExpenses;
	}
	public Set<Income> getUnplannedIncome() {
		return unplannedIncome;
	}
	public void setUnplannedIncome(Set<Income> unplannedIncome) {
		this.unplannedIncome = unplannedIncome;
	}
	
}
