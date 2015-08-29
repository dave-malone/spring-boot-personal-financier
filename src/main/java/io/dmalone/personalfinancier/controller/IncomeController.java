package io.dmalone.personalfinancier.controller;

import io.dmalone.personalfinancier.service.IncomeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/income")
public class IncomeController {

	private final IncomeService incomeService;

	@Autowired
	public IncomeController(IncomeService incomeService) {
		this.incomeService = incomeService;
	}
	
	
	
}
