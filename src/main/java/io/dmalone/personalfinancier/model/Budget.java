package io.dmalone.personalfinancier.model;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


/**
 * Represents an actual point-in-time budget. 
 * 
 * @author dmalone
 *
 */
public class Budget {

	private String id;
	private BudgetType budgetType;
	private Date startDate;
	private Date endDate;
	private Integer numberOfDaysWithinPayPeriod;
	
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
		this.plannedExpenses.addAll(plannedExpenses);
	}
	
	public Set<Date> getDatesInPayPeriod(){
		final Set<Date> datesInPayPeriod = new HashSet<Date>();
		
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.startDate);
		
		for(int i = 0; i < this.numberOfDaysWithinPayPeriod; i++){
			datesInPayPeriod.add(calendar.getTime());
			calendar.add(Calendar.DATE, 1);
		}
		
		return datesInPayPeriod;
	}

	public Set<Integer> getDaysOfMonthWithinPeriod() {
		final Set<Integer> daysWithinPeriod = new TreeSet<Integer>();
		
		final Calendar calendar = Calendar.getInstance();
		
		for(Date date : getDatesInPayPeriod()){
			calendar.setTime(date);
			daysWithinPeriod.add(calendar.get(Calendar.DATE));
		}
		
		return daysWithinPeriod;
	}

	public Integer getNumberOfDaysWithinPayPeriod() {
		return numberOfDaysWithinPayPeriod;
	}
	
	public void setNumberOfDaysWithinPayPeriod(int numberOfDaysWithinPayPeriod) {
		this.numberOfDaysWithinPayPeriod = new Integer(numberOfDaysWithinPayPeriod);
	}

	public void setNumberOfDaysWithinPayPeriod(Integer numberOfDaysWithinPayPeriod) {
		this.numberOfDaysWithinPayPeriod = numberOfDaysWithinPayPeriod;
	}

	public static Budget fromPrimaryIncome(Date forDate, Income income) {
		final Budget budget = new Budget();
		budget.addPlannedIncome(income);
		
		if(income.isPrimary() && income.isActive()){
			if(IncomeFrequency.BiWeekly == income.getIncomeFrequency()){
				budget.setBudgetType(BudgetType.BiWeekly);
				budget.setNumberOfDaysWithinPayPeriod(income.getNumberOfDaysInOnePayPeriod());
			}
		}
		
		budget.setStartDate(income.getPayPeriodStartDate(forDate));
		budget.setEndDate(income.getPayPeriodEndDate(forDate));
		
		return budget;
	}
	
}
