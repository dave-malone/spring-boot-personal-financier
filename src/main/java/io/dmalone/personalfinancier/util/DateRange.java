package io.dmalone.personalfinancier.util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class DateRange {

	private Date start;
	private Date end;
	
	public DateRange(){}
	
	public DateRange(Date start, Date end) {
		this.start = start;
		this.end = end;
	}

	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}
	
	public boolean isDateInRange(Date forDate){
		
		return forDate.compareTo(this.start) >= 0 && forDate.compareTo(DateUtil.maxOutTimeFields(this.end)) <= 0;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
	
	public int getNumberOfDaysInPeriod(){
		int numberOfDaysInPeriod = 0;
		
		final Calendar calendar = DateUtil.getZeroedOutCalendar(this.getStart());
		while(calendar.getTime().compareTo(this.getEnd()) <= 0){
			numberOfDaysInPeriod++;
			calendar.add(Calendar.DATE, 1);
		}
		
		return numberOfDaysInPeriod;
	}

	public boolean isDayOfMonthWithinRange(Integer dayOfMonth) {
		return getDays().contains(dayOfMonth);
	}

	public Set<Date> getDates() {
		final Set<Date> datesInPayPeriod = new HashSet<Date>();
		
		final Calendar calendar = DateUtil.getZeroedOutCalendar(this.getStart());
		
		for(int i = 0; i < getNumberOfDaysInPeriod(); i++){
			datesInPayPeriod.add(calendar.getTime());
			calendar.add(Calendar.DATE, 1);
		}
		
		return datesInPayPeriod;
	}

	public Set<Integer> getDays() {
		final Set<Integer> daysWithinPeriod = new TreeSet<Integer>();
		final Calendar calendar = DateUtil.getZeroedOutCalendar();
		
		for(Date date : getDates()){
			calendar.setTime(date);
			daysWithinPeriod.add(calendar.get(Calendar.DATE));
		}
		
		return daysWithinPeriod;
	}
	
	
	
}
