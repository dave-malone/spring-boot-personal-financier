package io.dmalone.personalfinancier.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum ExpenseType{
	Monthly("Monthly"), OneTime("One Time"), PerPaycheck("Per Paycheck");
	
	private final String name;
	
	private ExpenseType(String name){
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
	
	@JsonCreator
	public static ExpenseType forValue(String value){
		for(ExpenseType expenseType : values()){
			if(expenseType.name.equalsIgnoreCase(value)){
				return expenseType;
			}
		}
		
		return null;
	}
	
	@JsonValue
	public String toValue(){
		return this.name;
	}
	
}