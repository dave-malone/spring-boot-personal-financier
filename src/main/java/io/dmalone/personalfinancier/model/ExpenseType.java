package io.dmalone.personalfinancier.model;


public enum ExpenseType{
	Monthly("Monthly"), OneTime("One Time"), PerPaycheck("Per Paycheck");
	
	private final String name;
	
	private ExpenseType(String name){
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
	
}