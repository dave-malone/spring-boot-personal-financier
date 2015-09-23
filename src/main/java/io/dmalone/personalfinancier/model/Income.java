package io.dmalone.personalfinancier.model;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="income")
public class Income {

	public static final DateFormat DATE_FORMAT = new SimpleDateFormat("MMM-dd-yyyy");
	
	@Id 
	private String id;
	private Date dateCreated = new Date();
	private String name;
	private IncomeFrequency incomeFrequency;
	private BigDecimal amount;
	private Date date;
	private Date startDate;
	private Date endDate;

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
}
