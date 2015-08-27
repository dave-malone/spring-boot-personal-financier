package io.dmalone.personalfinancier.model;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;


public class Expense {

	@Id private String id;
	private Date dateCreated = new Date();
	
	private ExpenseType expenseType;
	private String name;
	private BigDecimal amount;
	private Integer dueDate;
	private boolean active = true;
	private Date start;
	private Date end;

	
	public String toString(){
		return String.format("Expense[Type: %s, Name: %s, Amount: %s, Due Date: %s]", expenseType, name, amount, dueDate);
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


	public Integer getDueDate() {
		return dueDate;
	}


	public void setDueDate(Integer dueDate) {
		this.dueDate = dueDate;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	public Date getStart() {
		return start;
	}


	public void setStart(Date start) {
		this.start = start;
	}


	public Date getEnd() {
		return end;
	}


	public void setEnd(Date end) {
		this.end = end;
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
}
