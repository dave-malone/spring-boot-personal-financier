package io.dmalone.personalfinancier.model;

import io.dmalone.personalfinancier.util.DateRange;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Represents an actual point-in-time budget. 
 * 
 * @author dmalone
 *
 */
public class Budget {

	private String id;
	private BudgetType budgetType;
	private Integer numberOfDaysWithinPayPeriod;
	private Date startDate;
	private Date endDate;
	private Set<Expense> plannedExpenses = new HashSet<Expense>();
	private Set<Income> plannedIncome = new HashSet<Income>();
	private Set<Expense> unplannedExpenses = new HashSet<Expense>();
	private Set<Income> unplannedIncome = new HashSet<Income>();
	private DateRange dateRange;
	
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
		return this.dateRange.getStart();
	}

	public Date getEndDate() {
		return this.dateRange.getEnd();
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
	
	public boolean hasExpenses(){
		return this.unplannedExpenses.isEmpty() != true && this.plannedExpenses.isEmpty() != true;
	}
	
	public boolean hasIncome(){
		return this.unplannedIncome.isEmpty() != true && this.plannedIncome.isEmpty() != true;
	}

	public void addPlannedIncome(List<Income> plannedIncome) {
		this.plannedIncome.addAll(plannedIncome);
	}

	public void addPlannedExpenses(List<Expense> plannedExpenses) {
		for(Expense expense : plannedExpenses){
			if(expense.isActiveDuringDateRange(dateRange)){
				this.plannedExpenses.add(expense);
			}
		}
	}
	
	public Set<Date> getDatesInPayPeriod(){
		return this.dateRange.getDates();
	}

	public Set<Integer> getDaysOfMonthWithinPeriod() {
		return this.dateRange.getDays();
	}

	public int getNumberOfDaysWithinPayPeriod() {
		return this.dateRange.getNumberOfDaysInPeriod();
	}


	public static Budget fromPrimaryIncome(Date forDate, Income income) {
		final Budget budget = new Budget();
		budget.addPlannedIncome(income);
		budget.setDateRange(income.getPayPeriodDateRange(forDate));
		
		if(income.isPrimary() && income.isActive() && IncomeFrequency.BiWeekly == income.getIncomeFrequency()){
			budget.setBudgetType(BudgetType.BiWeekly);
		}
		
		return budget;
	}


	public DateRange getDateRange() {
		return dateRange;
	}

	public void setDateRange(DateRange dateRange) {
		this.dateRange = dateRange;
		//TODO - remove superfluous fields, change access type to use methods instead of fields
		this.startDate = dateRange.getStart();
		this.endDate = dateRange.getEnd();
	}
	
}
