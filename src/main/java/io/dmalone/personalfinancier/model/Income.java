package io.dmalone.personalfinancier.model;

import io.dmalone.personalfinancier.util.DateUtil;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Income {

	public static final DateFormat DATE_FORMAT = new SimpleDateFormat("MMM-dd-yyyy");

	private String id;
	private Date dateCreated = new Date();
	private String name;
	private IncomeFrequency incomeFrequency;
	private BigDecimal amount;
	private Date date;
	private Date startDate;
	private Date endDate;
	private boolean active;
	private boolean primary;

	public Income(){
		//default constructor
	}
	
	public Income(String name, String amount, IncomeFrequency incomeFrequency){
		this.name = name;
		this.amount = new BigDecimal(amount);
		this.incomeFrequency = incomeFrequency;
	}
	
	public List<Date> getPayDatesInMonth(int year, String month) throws ParseException{
		List<Date> payDates = new ArrayList<Date>();
		
		Date firstOfTheMonth = DATE_FORMAT.parse(String.format("%s-01-%s", month, year));
		
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(firstOfTheMonth);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR, calendar.getActualMaximum(Calendar.HOUR));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
		
		Date lastOfTheMonth = calendar.getTime();
		
		if(incomeFrequency == IncomeFrequency.OneTime && (date.after(firstOfTheMonth) && date.before(lastOfTheMonth))){
			payDates.add(date);
		}else if(incomeFrequency == IncomeFrequency.BiWeekly){
			calendar.setTime(startDate);
			for(Date payDate = calendar.getTime(); payDate.before(lastOfTheMonth); payDate = calendar.getTime()){
				if(payDate.compareTo(firstOfTheMonth) >= 0){
					payDates.add(payDate);
				}	
				
				calendar.add(Calendar.DATE, 14);
			}
		}
		
		return payDates;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IncomeFrequency getIncomeFrequency() {
		return incomeFrequency;
	}

	public void setIncomeFrequency(IncomeFrequency incomeFrequency) {
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Income [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", incomeFrequency=");
		builder.append(incomeFrequency);
		builder.append(", startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append(", date=");
		builder.append(date);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result
				+ ((incomeFrequency == null) ? 0 : incomeFrequency.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Income other = (Income) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (incomeFrequency != other.incomeFrequency)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public void setAmount(String amount) {
		this.amount = new BigDecimal(amount);
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}
	

	public int getNumberOfDaysInOnePayPeriod() {
		int numberOfDaysInPayPeriod = 0;
		
		if(IncomeFrequency.BiWeekly == this.incomeFrequency){
			numberOfDaysInPayPeriod = 14;
		}else{
			throw new RuntimeException("This program is not yet capable of handling non-Biweekly income frequencies");
		}
		return numberOfDaysInPayPeriod;
	}
	
	public Date getPayPeriodStartDate(Date forDate){
		return getPayPeriodStartAndEndDates(forDate)[0];
	}
	
	public Date getPayPeriodEndDate(Date forDate){
		return getPayPeriodStartAndEndDates(forDate)[1];
	}

	Date[] getPayPeriodStartAndEndDates(Date forDate) {
		final Calendar calendar = DateUtil.getZeroedOutCalendar(this.startDate);
		
		Date startDate = calendar.getTime();
		calendar.add(Calendar.DATE, getNumberOfDaysInOnePayPeriod());
		Date endDate = calendar.getTime();
		
		while((forDate.compareTo(startDate) >= 0 && forDate.compareTo(endDate) <= 0) != true){
			calendar.add(Calendar.DATE, 1);
			startDate = calendar.getTime();
			calendar.add(Calendar.DATE, (getNumberOfDaysInOnePayPeriod() - 1));
			endDate = calendar.getTime();
		}
		
		return new Date[]{startDate, endDate};
	}
	
}
