package io.dmalone.personalfinancier.controller;

import io.dmalone.personalfinancier.model.BudgetType;

import java.beans.PropertyEditorSupport;

public class BudgetTypeEnumConverter extends PropertyEditorSupport{

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
        BudgetType budgetType = BudgetType.forValue(text);
        setValue(budgetType);
	}
	
}
