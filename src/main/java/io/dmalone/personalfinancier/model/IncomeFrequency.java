package io.dmalone.personalfinancier.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum IncomeFrequency {
	OneTime("One Time"), BiWeekly("Bi-Weekly"), SemiMonthly("Semi-Monthly");
	
	private final String name;
	
	private IncomeFrequency(String name){
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
	
	@JsonCreator
	public static IncomeFrequency forValue(String value){
		for(IncomeFrequency incomeFrequency : values()){
			if(incomeFrequency.name.equalsIgnoreCase(value)){
				return incomeFrequency;
			}
		}
		
		return null;
	}
	
	@JsonValue
	public String toValue(){
		return this.name;
	}
}