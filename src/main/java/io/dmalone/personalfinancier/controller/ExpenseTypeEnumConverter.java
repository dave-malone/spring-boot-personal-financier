package io.dmalone.personalfinancier.controller;

import io.dmalone.personalfinancier.model.ExpenseType;

import java.beans.PropertyEditorSupport;

public class ExpenseTypeEnumConverter extends PropertyEditorSupport{

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
        ExpenseType expenseType = ExpenseType.forValue(text);
        setValue(expenseType);
	}
	
}
