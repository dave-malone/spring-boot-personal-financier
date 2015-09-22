package io.dmalone.personalfinancier.model;

import java.math.BigDecimal;
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
	
	public BigDecimal getTotalPlannedIncome(){
		BigDecimal total = new BigDecimal(0.0);
		
		for(Income plannedIncome : plannedIncome){
			total = total.add(plannedIncome.getAmount());
		}
		
		return total;
	}
	
	public BigDecimal getTotalUnplannedIncome(){
		BigDecimal total = new BigDecimal(0.0);
		
		for(Income unplannedIncome : unplannedIncome){
			total = total.add(unplannedIncome.getAmount());
		}
		
		return total;
	}
	
	public BigDecimal getTotalIncome(){
		BigDecimal total = new BigDecimal(0.0);
		total = total.add(getTotalPlannedIncome());
		total = total.add(getTotalUnplannedIncome());
		
		return total;
	}
	
	public BigDecimal getTotalPlannedExpenses(){
		BigDecimal total = new BigDecimal(0.0);
		
		for(Expense plannedExpense : plannedExpenses){
			total = total.add(plannedExpense.getAmount());
		}
		
		return total;
	}
	
	public BigDecimal getTotalUnplannedExpenses(){
		BigDecimal total = new BigDecimal(0.0);
		
		for(Expense unplannedExpense : unplannedExpenses){
			total = total.add(unplannedExpense.getAmount());
		}
		
		return total;
	}
	
	public BigDecimal getTotalExpenses(){
		BigDecimal total = new BigDecimal(0.0);
		total = total.add(getTotalPlannedExpenses());
		total = total.add(getTotalUnplannedExpenses());
		
		return total;
	}
	
	public BigDecimal getRemainderAfterExpenses(){
		return getTotalIncome().subtract(getTotalExpenses());
	}
	
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

	public void addPlannedIncome(Income income) {
		this.plannedIncome.add(income);
	}

	public void addUnplannedIncome(Income income) {
		this.unplannedIncome.add(income);
	}

	public void addPlannedExpense(Expense expense) {
		this.plannedExpenses.add(expense);
	}
	
	public void addUnplannedExpense(Expense expense) {
		this.unplannedExpenses.add(expense);
	}
	
}
