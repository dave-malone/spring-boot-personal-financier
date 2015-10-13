package io.dmalone.personalfinancier.conversion;

import java.math.BigDecimal;
import java.util.Date;

public class ExpenseData {

	private Long id;
	private int version;
	private String expenseType;
	private String name;
	private BigDecimal amount;
	private Integer dueDate;
	private String active;
	private Date start;
	private Date end;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getExpenseType() {
		return expenseType;
	}
	public void setExpenseType(String expenseType) {
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
	public String isActive() {
		return active;
	}
	public void setActive(String active) {
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
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExpenseData [id=");
		builder.append(id);
		builder.append(", version=");
		builder.append(version);
		builder.append(", expenseType=");
		builder.append(expenseType);
		builder.append(", name=");
		builder.append(name);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", dueDate=");
		builder.append(dueDate);
		builder.append(", active=");
		builder.append(active);
		builder.append(", start=");
		builder.append(start);
		builder.append(", end=");
		builder.append(end);
		builder.append("]");
		return builder.toString();
	}

}
