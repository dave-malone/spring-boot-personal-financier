package io.dmalone.personalfinancier.model;


public enum IncomeFrequency {
	OneTime("One Time"), BiWeekly("Bi-Weekly"), SemiMonthly("Semi-Monthly");
	
	private final String name;
	
	private IncomeFrequency(String name){
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
}