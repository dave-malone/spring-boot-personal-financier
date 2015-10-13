package io.dmalone.personalfinancier.conversion;

import java.math.BigDecimal;
import java.util.Date;

public class IncomeData {

	private Long id;
	private int version;
	private String name;
	private String incomeFrequency;
	private BigDecimal amount;
	private Date date;
	private Date startDate;
	private Date endDate;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIncomeFrequency() {
		return incomeFrequency;
	}
	public void setIncomeFrequency(String incomeFrequency) {
		this.incomeFrequency = incomeFrequency;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IncomeData [id=");
		builder.append(id);
		builder.append(", version=");
		builder.append(version);
		builder.append(", name=");
		builder.append(name);
		builder.append(", incomeFrequency=");
		builder.append(incomeFrequency);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", date=");
		builder.append(date);
		builder.append(", startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append("]");
		return builder.toString();
	}
	
}
