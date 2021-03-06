package io.dmalone.personalfinancier.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum BudgetType {
	Monthly("Montly"), SemiMonthly("Semi-Monthly"), BiWeekly("Bi-Weekly");
	
	private final String name;
	
	private BudgetType(String name){
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
	
	@JsonCreator
	public static BudgetType forValue(String value){
		for(BudgetType budgetType : values()){
			if(budgetType.name.equalsIgnoreCase(value)){
				return budgetType;
			}
		}
		
		return null;
	}
	
	@JsonValue
	public String toValue(){
		return this.name;
	}

}