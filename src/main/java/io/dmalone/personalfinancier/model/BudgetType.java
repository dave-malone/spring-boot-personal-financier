package io.dmalone.personalfinancier.model;


public enum BudgetType {
	Monthly("Montly"), SemiMonthly("Semi-Monthly"), BiWeekly("Bi-Weekly");
	
	private final String name;
	
	private BudgetType(String name){
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}

}