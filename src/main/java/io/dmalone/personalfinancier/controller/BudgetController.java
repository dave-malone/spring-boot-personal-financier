package io.dmalone.personalfinancier.controller;

import io.dmalone.personalfinancier.repository.BudgetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BudgetController {

	private final BudgetRepository budgetRepository;

	@Autowired
	public BudgetController(BudgetRepository budgetRepository) {
		this.budgetRepository = budgetRepository;
	}
	
	@RequestMapping("/")
	public String index(Model model){
		model.addAttribute("name", "World!");
		return "index";
	}
	
}
