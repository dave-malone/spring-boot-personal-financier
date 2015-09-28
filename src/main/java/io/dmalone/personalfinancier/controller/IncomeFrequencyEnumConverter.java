package io.dmalone.personalfinancier.controller;

import io.dmalone.personalfinancier.model.IncomeFrequency;

import java.beans.PropertyEditorSupport;

public class IncomeFrequencyEnumConverter extends PropertyEditorSupport{

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
        IncomeFrequency incomeFrequency = IncomeFrequency.forValue(text);
        setValue(incomeFrequency);
	}
	
}
