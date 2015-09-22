package io.dmalone.personalfinancier.model;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;

/**
 * A multipurpose Expense class. Certain fields will be required based on the value
 * of the ExpenseType field. For instance, if the ExpenseType is a One Time expense,
 * then only the name, amount, and Day of Month Due fields are required. If the ExpenseType
 * is one of the recurring expense types, then at a minimum the name, amount, and 
 * start Date fields are required 
 * 
 * @author dmalone
 *
 */
public class Expense {

	@Id private String id;
	private Date dateCreated = new Date();
	
	private ExpenseType expenseType;
	private String name;
	private BigDecimal amount;
	
	/** only used if the ExpenseType is Monthly */
	private Integer dayOfMonthDue;
	
	/** only used for ExpenseType of One Time */
	private Date dueDate;
	
	private boolean active = true;
	
	/** only used for recurring ExpenseTypes such as Monthly or Per Paycheck */
	private Date startDate;
	/** only used for recurring ExpenseTypes such as Monthly or Per Paycheck */
	private Date endDate;

	public Expense(){
		//default constructor
	}
	
	public Expense(String name, String amount, ExpenseType expenseType){
		this.name = name;
		this.amount = new BigDecimal(amount);
		this.expenseType = expenseType;
	}
	
	
	public String toString(){
		return String.format("Expense[Type: %s, Name: %s, Amount: %s, Due Date: %s]", expenseType, name, amount, dayOfMonthDue);
	}


	public ExpenseType getExpenseType() {
		return expenseType;
	}


	public void setExpenseType(ExpenseType expenseType) {
		this.expenseType = expenseType;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public BigDecimal getAmount() {
		return amount;
	}


	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


	public Integer getDayOfMonthDue() {
		return dayOfMonthDue;
	}


	public void setDayOfMonthDue(Integer dayOfMonthDue) {
		this.dayOfMonthDue = dayOfMonthDue;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Date getDateCreated() {
		return dateCreated;
	}


	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
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


	public Date getDueDate() {
		return dueDate;
	}


	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
}
